package com.caesarcipher.controller;

import com.caesarcipher.algorithm.CaesarCipherAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CaesarCipherController {

    @Autowired
    private CaesarCipherAlgorithm caesarCipherAlgorithm;

    @CrossOrigin
    @GetMapping(path = "/encipher/{message}/{key}")
    public String encipher(@PathVariable String message, @PathVariable Integer key) {
        return this.caesarCipherAlgorithm.cipher(message, key);
    }
    @CrossOrigin
    @GetMapping(path = "/decipher/{message}/{key}")
    public String decipher(@PathVariable String message, @PathVariable Integer key) {
        return this.caesarCipherAlgorithm.decipher(message, key);
    }
    @CrossOrigin
    @GetMapping(path = "/break/{message}")
    public String breakCipher(@PathVariable String message) {
        return this.caesarCipherAlgorithm.breakCipher(message);
    }

}
