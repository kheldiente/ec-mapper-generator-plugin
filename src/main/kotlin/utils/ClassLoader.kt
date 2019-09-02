package utils

import java.net.URL
import java.net.URLClassLoader

object ClassLoader {

    fun getClass(path: String, name: String): Class<*> {
        val url = URL("file:///$path")
        val classLoader = URLClassLoader.newInstance(arrayOf(url))
        return classLoader.loadClass("")
    }

}