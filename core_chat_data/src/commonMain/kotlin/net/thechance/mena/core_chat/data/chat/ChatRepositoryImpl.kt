@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.data.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import net.thechance.mena.core_chat.data.utils.minusMinutes
import net.thechance.mena.core_chat.data.utils.now
import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.domain.exception.ChatNotFoundException
import net.thechance.mena.core_chat.domain.exception.SenderNotFoundException
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ChatRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// Dummy Implementation to simulate Real Datasource
class ChatRepositoryImpl : ChatRepository {
    private val messages = dummyMessagesEntities.toMutableList()

    override suspend fun getChatById(chatId: Uuid): Chat {
        if (chatId != dummyChat.id) {
            throw ChatNotFoundException("Chat with id=$chatId not found")
        }
        return dummyChat
    }

    override suspend fun getChatBySenderId(senderId: Uuid): Chat {
        val exists = messages.any { it.senderId == senderId }
        if (!exists) {
            throw SenderNotFoundException("No chat found for sender=$senderId")
        }
        return dummyChat
    }

    override fun getIncomingMessages(chatId: Uuid): Flow<Message> = channelFlow {
        if (chatId != dummyChat.id) {
            throw ChatNotFoundException("Chat with id=$chatId not found")
        }

        messages.maxByOrNull { it.sendAt }?.let { send(it) }

        // Simulate incoming real-time messages
        launch(Dispatchers.IO) {
            while (isActive) {
                delay(10000)
                val newMessage = Message(
                    id = Uuid.random(),
                    senderId = noorId,
                    chatId = chatId,
                    text = "Incoming message at ${LocalDateTime.now()}",
                    status = MessageStatus.SENT,
                    sendAt = LocalDateTime.now()
                )
                messages.add(newMessage)
                send(newMessage)
            }
        }
    }

    override suspend fun getChatHistory(
        chatId: Uuid,
        pageNumber: Int,
        pageSize: Int
    ): PagedData<Message> {
        if (chatId != dummyChat.id) {
            throw ChatNotFoundException("Chat with id=$chatId not found")
        }

        val sorted = messages.sortedByDescending { it.sendAt }

        val fromIndex = (pageNumber - 1) * pageSize
        if (fromIndex >= sorted.size) {
            return PagedData(
                data = emptyList(),
                totalItems = messages.size,
                isLastPage = true
            )
        }

        val toIndex = minOf(fromIndex + pageSize, sorted.size)
        val page = sorted.subList(fromIndex, toIndex)

        val isLast = toIndex >= sorted.size
        return PagedData(page, totalItems = messages.size, isLastPage = isLast)
    }

    override suspend fun sendMessage(chatId: Uuid, message: Message) {
        if (chatId != dummyChat.id) {
            throw ChatNotFoundException("Chat with id=$chatId not found")
        }
        if (message.senderId != bilalId && message.senderId != noorId) {
            throw SenderNotFoundException("Sender with id=${message.senderId} not found")
        }

        messages.add(message.copy(sendAt = LocalDateTime.now()))
    }

    // temp dummy datasource
    companion object {

        // Fixed participants + chat UUIDs
        val chatId = Uuid.parse("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        val bilalId = Uuid.parse("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        val noorId = Uuid.parse("cccccccc-cccc-cccc-cccc-cccccccccccc")

        val dummyMessagesEntities = listOf(
            // 🟢 Newest - Sending
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000020"),
                senderId = noorId,
                text = "Uploading now… please wait a sec ⏳",
                chatId = chatId,
                status = MessageStatus.Loading,
                sendAt = LocalDateTime.now().minusMinutes(2)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000019"),
                senderId = bilalId,
                text = "Argh, my internet dropped right when I was sending the file. Typical! 😤",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(5)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000018"),
                senderId = noorId,
                text = "Haha, no worries. Happens to me all the time.",
                chatId = chatId,
                status = MessageStatus.Failed,
                sendAt = LocalDateTime.now().minusMinutes(7)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000017"),
                senderId = bilalId,
                text = "Quick question—are you free this weekend? I was thinking maybe we could finally test the prototype together.",
                chatId = chatId,
                status = MessageStatus.SENT,
                sendAt = LocalDateTime.now().minusMinutes(10)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000016"),
                senderId = noorId,
                text = "Yes! I’d love that. Honestly, I’ve been waiting to see how it looks in action after all these months of development.",
                chatId = chatId,
                status = MessageStatus.SENT,
                sendAt = LocalDateTime.now().minusMinutes(12)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000015"),
                senderId = bilalId,
                text = "Perfect 👌",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(15)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000014"),
                senderId = noorId,
                text = "By the way, I remembered something funny from last year. Remember that bug where every single button in the app turned red? 😂 We freaked out thinking the database was corrupted.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(17)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000013"),
                senderId = bilalId,
                text = "Hahaha oh my god, yes. I still have a screenshot of that somewhere. I called it ‘The Red Wedding’ of our app. 😅",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(20)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000012"),
                senderId = noorId,
                text = "😂😂 Stop, I’m dying. You really should make a blooper reel of all our funniest dev moments.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(22)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000011"),
                senderId = bilalId,
                text = "Not a bad idea, actually. Could be a fun lightning talk for the next dev meetup.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(25)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000010"),
                senderId = noorId,
                text = "Yessss, do it!",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(28)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000009"),
                senderId = bilalId,
                text = "Anyway, speaking of meetups—did you ever hear back from that recruiter? You mentioned you had an interview lined up.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(30)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000008"),
                senderId = noorId,
                text = "Oh yeah, that! It went surprisingly well. The questions were tough, but I managed to talk through my thought process instead of panicking. I think they liked that.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(33)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000007"),
                senderId = bilalId,
                text = "That’s the way to do it 👏 They care more about how you think than just the final answer.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(35)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000006"),
                senderId = noorId,
                text = "True true. Fingers crossed 🤞",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(37)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000005"),
                senderId = bilalId,
                text = "You’ll crush it, I know you will.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(40)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000004"),
                senderId = noorId,
                text = "Awww thanks 😊",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(43)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000003"),
                senderId = bilalId,
                text = "Just speaking facts 😎",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(45)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000002"),
                senderId = noorId,
                text = "Haha okay okay, Mr. Confidence.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(47)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-000000000001"),
                senderId = bilalId,
                text = "What can I say, I’m on a roll today.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(50)
            ),

            // Older bilal messages
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000b1"),
                senderId = bilalId,
                text = "Hey",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(80)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000b2"),
                senderId = bilalId,
                text = "Are you there?",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(1350)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000b3"),
                senderId = bilalId,
                text = "I just wanted to ask something real quick.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(1400)
            ),

            // Older noor messages
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000n1"),
                senderId = noorId,
                text = "Hey Bilal 👋 just saw your messages.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(2800)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000n2"),
                senderId = noorId,
                text = "Sorry, I was away from my phone.",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(2870)
            ),
            Message(
                id = Uuid.parse("00000000-0000-0000-0000-0000000000n3"),
                senderId = noorId,
                text = "What’s up?",
                chatId = chatId,
                status = MessageStatus.Read,
                sendAt = LocalDateTime.now().minusMinutes(2880)
            )
        )

        val dummyChat = Chat(
            id = chatId,
            name = "Bilal Azzam",
            imageUrl = "https://avatars.githubusercontent.com/u/75501067?v=4"
        )
    }
}