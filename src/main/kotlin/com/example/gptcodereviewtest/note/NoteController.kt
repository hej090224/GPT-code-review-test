package com.example.gptcodereviewtest.note

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService,
) {
    @GetMapping
    fun getNotes(
        @RequestParam(required = false, defaultValue = "false") includeDeleted: Boolean,
        @RequestParam(required = false) keyword: String?,
    ): List<Note> {
        return noteService.findAll(includeDeleted, keyword)
    }

    @GetMapping("/{id}")
    fun getNote(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(noteService.findById(id) ?: "")
    }

    @PostMapping
    fun createNote(@RequestBody note: Note): Note {
        return noteService.create(note)
    }

    @PutMapping("/{id}")
    fun updateNote(
        @PathVariable id: Long,
        @RequestBody note: Note,
    ): Note {
        return noteService.update(id, note)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(
        @PathVariable id: Long,
        @RequestParam(required = false) password: String?,
    ): ResponseEntity<String> {
        val deleted = noteService.delete(id, password)
        return if (deleted) {
            ResponseEntity.ok("deleted")
        } else {
            ResponseEntity.ok("wrong password")
        }
    }

    @PostMapping("/{id}/restore")
    fun restoreNote(@PathVariable id: Long): String {
        noteService.restore(id)
        return "ok"
    }

    @GetMapping("/debug/dump")
    fun dump(): Map<Long, Note> {
        return noteService.debugDump()
    }
}
