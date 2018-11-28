package ge.chalauri.transferapi.controller;

import ge.chalauri.transferapi.entity.Transfer;
import ge.chalauri.transferapi.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferService service;

    @Autowired
    public TransferController(TransferService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Transfer create(@RequestBody Transfer transfer) {
        return service.makeTransfer(transfer);
    }
}
