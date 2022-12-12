package pt.isec.pdrestapi.controllers;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isec.pdrestapi.models.LoremConfig;

@RestController
@RequestMapping("lorem")
public class LoremController
{
    private ResponseEntity generateLorem(String type, Integer length)
    {
        Lorem lorem = LoremIpsum.getInstance();

        switch(type.toLowerCase())
        {
            case "word" ->
            {
                return ResponseEntity.ok(lorem.getWords(length));
            }
            case "paragraph" ->
            {
                return ResponseEntity.ok(lorem.getParagraphs(length, length));
            }
            default ->
            {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Invalid type.");
            }
        }
    }

    @GetMapping("{type}")
    public ResponseEntity getText(@PathVariable("type") String type,
                                  @RequestParam(value="length", required=false) Integer length)
    {
        if (length == null)
            length = 1;

        return generateLorem(type, length);
    }

    @PostMapping
    public ResponseEntity postText(@RequestBody LoremConfig config)
    {
        if (config.getType() == null)
            return ResponseEntity.badRequest().body("Type is mandatory.");
        if (config.getLength() == null)
            config.setLength(1);

        return generateLorem(config.getType(), config.getLength());
    }
}
