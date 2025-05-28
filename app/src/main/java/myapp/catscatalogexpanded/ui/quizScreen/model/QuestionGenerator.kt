package myapp.catscatalogexpanded.ui.quizScreen.model

import myapp.catscatalogexpanded.ui.breedsScreen.data.model.Breed

object QuestionGenerator {
    fun generateQuestions(breeds: List<Breed>): List<Question> {
        val usedImages = mutableSetOf<String>()
        val result = mutableListOf<Question>()

        while (result.size < 20 && breeds.isNotEmpty()) {
            val breed = breeds.random()
            val image = breed.image

            if (usedImages.contains(image?.url)) continue

            image?.url?.let { usedImages.add(it) }

            val questionType =
                if (result.size % 2 == 0) QuestionType.GUESS_BREED else QuestionType.ODD_ONE_OUT

            val options = when (questionType) {
                QuestionType.GUESS_BREED -> {
                    val wrong =
                        breeds.filter { it.id != breed.id }.shuffled().take(3).map { it.name }
                    (wrong + breed.name).shuffled()
                }

                QuestionType.ODD_ONE_OUT -> {
                    val correctTemperaments = breed.temperament.split(", ").toSet()
                    val wrong = listOf("Lazy", "Aggressive", "Fearless", "Friendly")
                        .filterNot { it in correctTemperaments }
                        .shuffled().take(1)
                    val mixed = (correctTemperaments.shuffled().take(3) + wrong).shuffled()
                    mixed
                }
            }

            val correctIndex = options.indexOf(
                when (questionType) {
                    QuestionType.GUESS_BREED -> breed.name
                    QuestionType.ODD_ONE_OUT -> {
                        val temperamentList = breed.temperament.split(", ")
                        options.first { it !in temperamentList }
                    }
                }
            )

            image?.url?.let {
                result.add(
                    Question(
                        questionID = result.size,
                        imageUrl = it,
                        questionType = questionType,
                        options = options,
                        correctIndex = correctIndex
                    )
                )
            }
        }

        return result
    }
}