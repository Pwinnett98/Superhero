package com.example.superhero.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
)
data class heroVuln (
    @StringRes val vuln: Int,
    @StringRes val vulndetail: Int
)