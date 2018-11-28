package ge.chalauri.transferapi.controller;

import ge.chalauri.transferapi.entity.Transfer;
import ge.chalauri.transferapi.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    private TransferController controller;

    @Before
    public void init() {
        controller = new TransferController(transferService);
    }

    @Test
    public void test_create_should_call_service_method() {
        Transfer transfer = mockTransfer("src001", "dst001");

        when(transferService.makeTransfer(any(Transfer.class))).thenReturn(transfer);
        controller.create(transfer);
        verify(transferService, times(1)).makeTransfer(any(Transfer.class));
    }

    private Transfer mockTransfer(String source, String destionation) {
        Transfer transfer = new Transfer();
        transfer.setAmount(100.0D);
        transfer.setSourceAccountNumber(source);
        transfer.setDestinationAccountNumber(destionation);
        transfer.setDescription("Monthly payment");

        return transfer;
    }
}
