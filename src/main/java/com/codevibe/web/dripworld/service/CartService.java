package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.dao.*;
import com.codevibe.web.dripworld.entities.CartEntity;
import com.codevibe.web.dripworld.entities.StockEntity;
import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.repositories.CartRepository;
import com.codevibe.web.dripworld.repositories.StockRepository;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    @Inject
    private CartRepository cartRepository;

    @Inject
    private UserService userService;

    @Inject
    private StockService stockService;


    public Response save(CartDAO cartDAO, HttpServletRequest request) {
        if (cartDAO == null || cartDAO.getStockId() == null || cartDAO.getQuantity() == null || cartDAO.getQuantity() < 1) {

            return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "add to Cart Request Failed");

        }

        final Long Stock_ID = cartDAO.getStockId();

        System.out.println("STOCK ID AT" + Stock_ID);

        final UsersEntity user = userService.extractUserFromRequest(request);

//        stockService.stockRepository.findById(Stock_ID).orElseThrow(()->new ValidationFailedException("Stock not found"));



//        if (!stockRepository.findById(cartDAO.getStockId())) {
//            return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR, null, "Stock Not Found");
//        }

        final StockEntity stock = stockService.getStockRepository().findById(cartDAO.getStockId()).get();

        final CartEntity cart = cartRepository.findByUserAndStock(user, stock).orElse(null);
        if (cart == null) {
            if (cartDAO.getQuantity() > stock.getQty()) {
                return ResponseUtil.generate(Response.Status.OK, null, "cart quantity is larger than available stock");
            }
            final CartEntity item = new CartEntity(cartDAO.getQuantity(), user, stock);
            cartRepository.save(item);
            return ResponseUtil.generate(Response.Status.OK, null, "Item Added to Cart Successfully");
        } else {

            System.out.println("Cart Already Exists");
            if ((cartDAO.getQuantity() + cart.getQuantity()) > stock.getQty())
                return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR, null, "cart quantity is larger than available stock");

            cart.setQuantity(cart.getQuantity() + cartDAO.getQuantity());
            cartRepository.update(cart);

            return ResponseUtil.generate(Response.Status.OK, null, "Cart Updated Successfully");
        }


    }

    public void deleteById(Long id,HttpServletRequest request){
        final UsersEntity user = userService.extractUserFromRequest(request);
        final CartEntity cart = cartRepository.findByIdAndUser(id,user)
                .orElseThrow(()-> new ValidationFailedException("Item Not Found"));
        cartRepository.deleteById(id);
    }

    public List<CartDAO> getAllByUser(HttpServletRequest request) {
        final UsersEntity user = userService.extractUserFromRequest(request);
        final List<CartDAO> list = new ArrayList<>();
        for (CartEntity cart : cartRepository.findAllByUser(user)) {
            if (cart != null) {

                final CartDAO cartDAO = new CartDAO();
                cartDAO.setId(cart.getId());
                cartDAO.setQuantity(cart.getQuantity());
                cartDAO.setUserDAO(
                        new UserDAO(
                                cart.getUsersByUsersId().getId(),
                                cart.getUsersByUsersId().getFirstName(),
                                cart.getUsersByUsersId().getLastName()
                        )
                );
                cartDAO.setStockDAO(
                        new StockDAO(
                                cart.getStockByStockId().getId(),
                                cart.getStockByStockId().getPrice(),
                                cart.getStockByStockId().getQty(),
                                new ProductDAO(
                                        cart.getStockByStockId().getProductByProductId().getId(),
                                        cart.getStockByStockId().getProductByProductId().getName(),
                                        cart.getStockByStockId().getProductByProductId().getDescription(),
                                        cart.getStockByStockId().getProductByProductId().getImages(),
                                        new CategoryDAO(
                                                cart.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getId(),
                                                cart.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getName(),
                                                null
                                        )

                                )

                        )
                );

                list.add(cartDAO);
            }


        }
        return list;

    }

    public Response changeQuantity(CartDAO cartDAO,HttpServletRequest request){
        if(cartDAO == null || cartDAO.getQuantity() == null || cartDAO.getQuantity()<1){
           return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Invalid Request");
        }

        final UsersEntity user = userService.extractUserFromRequest(request);

        if(cartRepository.findByIdAndUser(cartDAO.getId(),user).isPresent()){

            final CartEntity cart = cartRepository.findByIdAndUser(cartDAO.getId(),user).get();

            if(cartDAO.getQuantity()>cart.getStockByStockId().getQty()){
               return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR,null,"Unavailable Quantity Requested");
            }

            cart.setQuantity(cartDAO.getQuantity());
            cartRepository.update(cart);

            return ResponseUtil.generate(Response.Status.OK,null,"quantity updated");
        }else{
            return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR,null,"Cart Item Unavailable");
        }

    }

    public void clearCart(HttpServletRequest request){
        final UsersEntity user = userService.extractUserFromRequest(request);
        cartRepository.deleteUserCart(user);
    }


}
