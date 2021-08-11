package mariamormotsadze.gigakhizanishvili.messengerapp.data.models.user

object UserFactory {
    fun toServiceModel(model: UserModel) = UserServiceModel(
        model.nickname,
        model.imageUrl,
        model.profession,
    )

    fun toModel(userId: String, serviceModel: UserServiceModel) = UserModel(
        userId,
        serviceModel.nickname!!,
        serviceModel.imageUrl,
        serviceModel.profession!!,
    )
}