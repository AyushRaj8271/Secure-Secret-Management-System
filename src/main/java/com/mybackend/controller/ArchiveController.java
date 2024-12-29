package com.mybackend.controller;

import com.mybackend.dto.ArchiveDTO;
import com.mybackend.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveDTO> findById(@PathVariable Long id) {
        Optional<ArchiveDTO> archiveEntityDTO = archiveService.findById(id);
        return archiveEntityDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ArchiveDTO>> findAll() {
        List<ArchiveDTO> archiveEntityDTOs = archiveService.findAll();
        return ResponseEntity.ok(archiveEntityDTOs);
    }
}
