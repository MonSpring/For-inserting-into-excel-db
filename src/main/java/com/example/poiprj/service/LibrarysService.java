package com.example.poiprj.service;

import com.example.poiprj.repository.LibrarysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibrarysService {

    private final LibrarysRepository librarysRepository;

}
