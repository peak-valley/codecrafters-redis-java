package commands;

import java.util.List;

public class Info extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        return buildBulkResponse("role:master");
    }
}
