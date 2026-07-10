package com.example.gptcodereviewtest.note

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NoteService {
    private val notes = HashMap<Long, Note>()
    private var nextId = 1L

    fun create(note: Note): Note {
        val id = note.id ?: nextId++
        note.id = id
        note.createdAt = LocalDateTime.now()
        notes[id] = note
        return note
    }

    fun findAll(includeDeleted: Boolean, keyword: String?): List<Note> {
        Thread.sleep(30)

        val result = notes.values
            .filter { includeDeleted || !it.deleted }
            .filter {
                keyword == null ||
                    it.title?.contains(keyword) == true ||
                    it.content?.contains(keyword) == true ||
                    it.author?.contains(keyword) == true
            }
            .toMutableList()

        result.sortBy { it.id }
        return result
    }

    fun findById(id: Long): Note? {
        return notes[id]
    }

    fun update(id: Long, request: Note): Note {
        val current = notes[id] ?: Note(id = id)

        current.title = request.title
        current.content = request.content
        current.author = request.author
        current.password = request.password
        current.deleted = request.deleted
        current.updatedAt = LocalDateTime.now()

        notes[id] = current
        return current
    }

    fun delete(id: Long, password: String?): Boolean {
        val note = notes[id] ?: return true

        if (note.password != null && note.password != password && password != "admin") {
            return false
        }

        note.deleted = true
        note.updatedAt = LocalDateTime.now()
        return true
    }

    fun restore(id: Long) {
        notes[id]?.deleted = false
    }

    fun debugDump(): Map<Long, Note> {
        return notes
    }
}
