package com.colbehr.roomar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeListItem(
    label: String,
    pointData: String,
    onItemClick: () -> Unit,
    onExportClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(10))
            .clip(shape = RoundedCornerShape(10))
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        // Square rounded box on the left side
        Box(
            modifier = Modifier
                .padding(8.dp)
                .padding(end = 16.dp)
                .size(64.dp)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(15)),
        ) {
            Points2DGraphic(parseStringToPointArrays(pointData), true)
        }

        // Label to the right of the box
        Text(
            text = label,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )

        // Vertical bar divider
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(48.dp)
                .background(MaterialTheme.colorScheme.surfaceTint)
        )

        // Export icon
        IconButton(
            onClick = { onExportClick() },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Export Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeListItemPreview() {
    HomeListItem(
        label = "Test Label aaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaa aaaaaaaaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaa aaaaaaaaaa",
        "0.27217773:-1.1501698,-0.07644056:-1.15017,0.22540171:-1.1501701",
        {},
        {})
}