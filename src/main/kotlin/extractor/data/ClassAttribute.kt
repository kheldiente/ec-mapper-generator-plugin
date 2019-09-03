package extractor.data

data class ClassAttribute(var name: String = "",
                          var type: String = "",
                          var readOnly: Boolean = false,
                          var isConstructorParameter: Boolean = false)