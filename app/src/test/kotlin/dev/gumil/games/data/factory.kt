package dev.gumil.games.data

import kotlin.random.Random

fun Game(
    id: Int = Random.nextInt()
) = Game(
    id = "$id",
    cover = GameImage(
        imageId = "${Random.nextInt()}"
    ),
    firstReleaseDate = Random.nextLong(),
    gameEngines = listOf(
        FieldName("${Random.nextInt()}")
    ),
    gameModes = listOf(
        FieldName("${Random.nextInt()}")
    ),
    genres = listOf(
        FieldName("${Random.nextInt()}")
    ),
    involvedCompanies = listOf(
        InvolvedCompany(
            company = FieldName("${Random.nextInt()}")
        )
    ),
    name = "${Random.nextInt()}",
    platforms = listOf(
        GamePlatform(
            name = "${Random.nextInt()}",
            platformLogo = GameImage(
                imageId = "${Random.nextInt()}"
            )
        )
    ),
    playerPerspectives = listOf(
        FieldName("${Random.nextInt()}")
    ),
    screenshots = listOf(
        GameImage(
            imageId = "${Random.nextInt()}"
        )
    ),
    storyline = "${Random.nextInt()}",
    summary = "${Random.nextInt()}",
    themes = listOf(
        FieldName("${Random.nextInt()}")
    ),
    totalRating = Random.nextDouble(),
    url = "${Random.nextInt()}",
    videos = listOf(
        GameVideo(
            name = "${Random.nextInt()}",
            videoId = "${Random.nextInt()}"
        )
    )
)
