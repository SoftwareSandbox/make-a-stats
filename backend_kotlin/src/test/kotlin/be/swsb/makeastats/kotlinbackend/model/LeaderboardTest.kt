package be.swsb.makeastats.kotlinbackend.model

import org.assertj.core.api.Assertions
import org.junit.Test

class LeaderboardTest {

    @Test
    fun generateUniqueId_GeneratesAUniqueIdWithEveryCall() {
        Assertions.assertThat(Leaderboard.generateUniqueId())
                .isNotEqualTo(Leaderboard.generateUniqueId())
                .isNotEqualTo(Leaderboard.generateUniqueId())
    }

}