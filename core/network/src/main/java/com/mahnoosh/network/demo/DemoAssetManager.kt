package com.mahnoosh.network.demo

import java.io.InputStream

interface DemoAssetManager {
    fun open(fileName: String): InputStream
}