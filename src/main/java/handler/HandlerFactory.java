package handler;

import request.ParsedRequest;

public class HandlerFactory {
    // routes based on the path. Add your custom handlers here
    public static BaseHandler getHandler(ParsedRequest request) {
        String path = request.getPath();
        
        if (path.startsWith("/api/transactions")) {
            return new TransactionHandler();
        } else if (path.startsWith("/api/users")) {
            return new UserHandler();
        } else if (path.startsWith("/api/transfer")) {
            return new TransferHandler();
        } else if (path.equals("/")) {
            return new HomeHandler();
        } else {
            return new NotFoundHandler();
        }
    }
}
