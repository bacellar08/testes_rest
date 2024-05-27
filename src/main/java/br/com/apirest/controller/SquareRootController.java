package br.com.apirest.controller;

import br.com.apirest.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SquareRootController {

    @RequestMapping("/sqr/{number}")
    public Double sqr(@PathVariable("number") String numberOne) throws Exception {
        if (!isNumeric(numberOne)) {
            throw new ResourceNotFoundException("Please set a numeric value.");
        }

        return Math.sqrt(convertToDouble(numberOne));
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) {
            return 0D;
        }
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
