package commands;

import java.util.List;

public class Ping extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        return buildBulkResponse("PONG");
    }
}
