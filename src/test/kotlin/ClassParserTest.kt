import exception.ClassParserException
import org.junit.Test
import utils.ClassParser
import kotlin.test.assertEquals

class ClassParserTest {

    @Test(expected = ClassParserException::class)
    fun getPackageNameUsingProjectPath() {
        val path = "com.example.test.data.StudentEntity.kt"

        val packageName = ClassParser.getPackageName(path)

        assertEquals("com.example.test.data", packageName)
    }

}