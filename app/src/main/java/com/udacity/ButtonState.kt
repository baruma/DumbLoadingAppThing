package com.udacity


sealed class ButtonState {
    // Changed States because 3 isn't necessary.

    object Ready : ButtonState()
    object Downloading : ButtonState()
}