package Server.Controllers;

import Server.Models.BillboardModel;

public class BillboardController {
    private BillboardModel model;
    public BillboardController(BillboardModel model){
        this.model = model;
    }
    public String getBillboard(){
        return "Billboard xml";
    }
}
