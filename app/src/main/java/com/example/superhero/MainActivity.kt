package com.example.superhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superhero.data.DataSource
import com.example.superhero.model.Hero
import com.example.superhero.ui.theme.SuperheroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperheroApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperheroTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperheroApp() {
    Scaffold (
        topBar = {
            SuperheroTopAppBar()
        }
    ) {it ->
        LazyColumn(contentPadding = it) {
            items(DataSource.Hero) {
                HeroItem(
                    hero = it,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun HeroItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer
    )

    Card(modifier = modifier.clickable { expanded = !expanded }) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(hero.imageResourceId),
                    contentDescription = null
                )

                Column {
                    Text(
                        text = stringResource(hero.name),
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = stringResource(hero.des),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                HeroItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                Text(
                    text = stringResource(hero.vuln),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(hero.vulnDetail),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun HeroItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}


@Preview
@Composable
fun SuperheroPreview() {
    SuperheroTheme {
        SuperheroApp()
    }
}

@Preview
@Composable
fun SuperheroDarkThemePreview() {
    SuperheroTheme(darkTheme = true) {
        SuperheroApp()
    }
}