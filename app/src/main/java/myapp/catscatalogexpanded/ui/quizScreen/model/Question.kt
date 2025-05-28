package myapp.catscatalogexpanded.ui.quizScreen.model

data class Question(
    val questionID: Int,
    val imageUrl: String,
    val questionType: QuestionType,
    val options: List<String>,
    val correctIndex: Int
)