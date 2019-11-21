package railwaysProject.model;

public class BookResponse {

    private boolean success;
    private int[] ticket_id;


    public boolean isSuccess() {
        return success;
    }

    public BookResponse(boolean success, int[] ticket_id) {
        this.success = success;
        this.ticket_id = ticket_id;
    }

    public int[] getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int[] ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
