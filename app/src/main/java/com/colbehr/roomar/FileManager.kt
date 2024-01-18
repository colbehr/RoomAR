package com.colbehr.roomar

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.gson.Gson
import de.javagl.obj.ObjWriter
import de.javagl.obj.Objs
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.security.MessageDigest


/**
 * Saves the json string to a local app file not accessible by the user
 * returns a filename
 * TODO: remove file name generation and pass it instead
 */
fun saveStringToFile(context: Context, jsonString: String): String {
    try {
        val fileName = generateHash(jsonString) + ".json"
        val outputStream: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        outputStream.write(jsonString.toByteArray())
        outputStream.close()
        return fileName
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * Saves an obj file to a local app file not accessible by the user
 * returns a uri to the file
 */
fun createOBJFile(context: Context, jsonString: String): Uri? {
    val gson = Gson()
    val pointData = gson.fromJson(jsonString, PointData::class.java)

    //covert point data to obj data
    val obj = Objs.create()
    for (point in pointData.points) {
        obj.addVertex(point.x, 0f, point.y)
        obj.addNormal(0f, 0f, 1f)
    }

    val numPoints = pointData.points.size

    val vertexIndices = IntArray(numPoints) { it }
    val normalIndices = IntArray(numPoints) { 0 }
    obj.addFace(vertexIndices, null, normalIndices)

    try {
        val fileName = generateHash(jsonString) + ".obj"
        val file = File(context.filesDir, fileName)
        //save obj to file
        val outputStream = FileOutputStream(file)
        ObjWriter.write(obj, outputStream)
        outputStream.close()

        //return URI for share intent
        return FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun generateHash(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bytes = md.digest(input.toByteArray())
    val sb = StringBuilder()
    for (byte in bytes) {
        sb.append(String.format("%02x", byte))
    }
    return sb.toString()
}


/**
 * takes a file name and returns a URI
 */
fun readFileUri(context: Context, fileName: String): Uri? {
    return try {
        val file = File(context.filesDir, fileName)

        if (file.exists()) {
            // Get the content URI for the file
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Reads a file for home page item uses
 */
fun readFile(context: Context, fileName: String): String {
    return try {
        val inputStream = context.openFileInput(fileName)
        val reader = InputStreamReader(inputStream)
        val buffer = CharArray(1024)
        val builder = StringBuilder()
        var charsRead: Int

        while (reader.read(buffer).also { charsRead = it } > 0) {
            builder.appendRange(buffer, 0, charsRead)
        }

        inputStream.close()
        builder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * gets all files and gets sets up a map with <filename, pointdata>
 *     returns so the home page can set up the home page items
 */
fun getAllFilesContentInInternalStorage(context: Context): Map<String, PointData> {
    if (context.filesDir == null) {
        return emptyMap()
    }

    val filesDir: File = context.filesDir
    val fileContentsMap = mutableMapOf<String, PointData>()
    val gson = Gson()

    val allFiles: Array<String> = filesDir.list() ?: emptyArray()

    for (fileName in allFiles) {
        if (fileName.endsWith(".json")) {
            val fileContents = readFile(context, fileName)

            try {
                val pointData = gson.fromJson(fileContents, PointData::class.java)

                fileContentsMap[fileName] = pointData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    return fileContentsMap
}
