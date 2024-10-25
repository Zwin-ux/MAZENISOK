package handler;

import request.ParsedRequest;

public class HandlerFactory {
    public static BaseHandler getHandler(ParsedRequest request) {
        String path = request.getPath();
        String method = request.getMethod();
        
        if (path.startsWith("/api/transactions")) {
            if (method.equals("POST") && path.endsWith("/deposit")) {
                return new CreateDepositHandler();
            } else if (method.equals("GET")) {
                return new GetTransactionsHandler();
            }
        } else if (path.startsWith("/api/users")) {
            if (method.equals("POST")) {
                return new CreateUserHandler();
            } else if (method.equals("GET")) {
                return new GetUserHandler();
            }
        } else if (path.startsWith("/api/transfer")) {
            if (method.equals("POST")) {
                return new TransferHandler();
            }
        } else if (path.equals("/")) {
            return new HomeHandler();
        }
        
        // If no handler matched, return NotFoundHandler
        return new NotFoundHandler();
    }
}
