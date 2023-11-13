package com.duolingo.data.converter

import com.duolingo.data.net.dto.UserDTO
import com.duolingo.data.persistence.entity.UserEntity
import com.duolingo.domain.model.Streak
import com.duolingo.domain.model.User
import com.duolingo.domain.model.id.LongId
import javax.inject.Inject

/**
 * A converter class used to convert [UserDTO] or [UserEntity] (in the data layer) to [User]
 * in the domain layer and vice versa.
 */
class UserConverter
@Inject constructor() {

    /**
     * Convert a [UserDTO] into an [User].
     * @param dto   Object to be transformed.
     *
     * @return [User] if [UserDTO] is valid
     */
    fun convert(dto: UserDTO): User =
        User(
            id = dto.id,
            name = dto.name,
            age = dto.age,
            email = dto.email,
            isTrialUser = dto.isTrialUser,
            currentCourseId = dto.currentCourseId,
            streak =  dto.streak,
            xp = dto.xp,
            )

    /**
     * Convert a [UserEntity] into an [User].
     * @param entity    Object to be transformed.
     *
     * @return [User] if [UserEntity] is valid, otherwise null.
     */
    fun convert(entity: UserEntity): User =
        User(
            id = LongId(entity.id),
            name = entity.name,
            age = entity.age,
            email = entity.email,
            isTrialUser = entity.isTrialUser,
            currentCourseId = entity.currentCourseId,
            streak = Streak(entity.longestStreak, entity.currentStreak),
            xp = entity.xp,
        )

    /**
     * Transform a [User] into an [UserEntity].
     * @param user  [User] object to be transformed.
     *
     * @return [User] if [UserEntity] is valid, otherwise null.
     */
    fun convertToEntity(user: User): UserEntity =
        UserEntity(
            id = user.id.get(),
            name = user.name,
            age = user.age,
            email = user.email,
            isTrialUser = user.isTrialUser,
            currentCourseId = user.currentCourseId,
            longestStreak = user.streak.longestStreak,
            currentStreak = user.streak.currentStreak,
            xp = user.xp,
        )
}