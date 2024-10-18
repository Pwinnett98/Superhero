package com.example.superhero.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val des: Int,
    @StringRes val vuln: Int,
    @StringRes val vulnDetail: Int
)