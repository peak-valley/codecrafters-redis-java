package commands;

import java.util.List;

public class Echo extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        return buildBulkResponse(new String((byte[]) content.get(1)));
    }
}
