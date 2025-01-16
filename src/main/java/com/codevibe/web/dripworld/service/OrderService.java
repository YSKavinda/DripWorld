package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.constants.OrderStatus;
import com.codevibe.web.dripworld.constants.PaymentMethods;
import com.codevibe.web.dripworld.dao.*;
import com.codevibe.web.dripworld.entities.*;
import com.codevibe.web.dripworld.handler.types.ProcessFailedException;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.repositories.*;
import com.codevibe.web.dripworld.util.AppUtil;
import com.codevibe.web.dripworld.util.Encryption;
import com.codevibe.web.dripworld.util.Env;
import com.codevibe.web.dripworld.util.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class OrderService {

    private static Double Delivery_Charges = 600.00;

//    @Inject
//    private SettingsRepository settingsRepository;
    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrderItemsRepository orderItemsRepository;

    @Inject
    private UserService userService;

    @Inject
    private CartRepository cartRepository;

    @Inject
    private StockRepository stockRepository;

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private Encryption encryption;



//    private static OrderItemsEntity getOrderItem(CartEntity cart, OrdersEntity order){
//        final OrderItemsEntity item = new OrderItemsEntity();
//        item.setOrdersByOrdersId(order);
//        item.setStockByStockId(cart.getStockByStockId());
//        item.setPrice(cart.getStockByStockId().getPrice());
//        if(cart.getQuantity()>cart.getStockByStockId().getQty()){
//            throw new RuntimeException("Not Enough Stock Available");
//        }
//        item.setQty(cart.getQuantity());
//        return item;
//    }

    public List<OrdersEntity> getByUser(UsersEntity user) {
        return ordersRepository.findAllByUser(user);
    }

    public List<OrderDAO> getAllOrdersByCurrentUser(HttpServletRequest request) {
        final UsersEntity user = userService.extractUserFromRequest(request);
        final List<OrderDAO> list = new ArrayList<>();
        for (OrdersEntity orders : getByUser(user)) {

            final OrderDAO dao = new OrderDAO();
            dao.setId(orders.getId());
            dao.setOrderDate(orders.getOrderDate());
            dao.setCode(orders.getCode());
            dao.setPaymentMethod(orders.getPaymentMethod());
            dao.setStatus(orders.getStatus());
            dao.setDelivery(orders.getDelivery());
            dao.setTotal(orders.getTotal());
            dao.setStatus(orders.getStatus());
            dao.setUserDAO(
                    new UserDAO(
                            orders.getUsersByUsersId().getId(),
                            orders.getUsersByUsersId().getFirstName(),
                            orders.getUsersByUsersId().getFirstName(),
                            orders.getUsersByUsersId().getContact()
                    )
            );

                List<OrderItemsEntity>orderItems = orderItemsRepository.getOrderItemsByOrderId(orders.getId());
                List<OrderItemsDAO> newList = new ArrayList<>();
//                orderItems.forEach((item)->{
            for(OrderItemsEntity item : orderItems) {
                final OrderItemsDAO newItem = new OrderItemsDAO();
                newItem.setId(item.getId());
                newItem.setPrice(item.getPrice());
                newItem.setOrder(null);
                newItem.setStockDAO(
                        new StockDAO(
                                item.getStockByStockId().getId(),
                                item.getStockByStockId().getPrice(),
                                item.getStockByStockId().getQty(),
                                new ProductDAO(
                                        item.getStockByStockId().getProductByProductId().getId(),
                                        item.getStockByStockId().getProductByProductId().getName(),
                                        item.getStockByStockId().getProductByProductId().getDescription(),
                                        item.getStockByStockId().getProductByProductId().getImages(),
                                        new CategoryDAO(
                                                item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getId(),
                                                item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getName(),
                                                item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getDescription()
                                        )
                                )
                        )
                );
                newList.add(newItem);
//                });
            }
            dao.setOrderItems(
                  newList
            );


            list.add(dao);
        }
        return list;
    }


    public Response ordersFromCart(HttpServletRequest request, String invoiceId, PaymentMethods paymentMethod, String city, String street, String zip) {
        if (paymentMethod == null || (paymentMethod.equals(PaymentMethods.card) && (invoiceId == null || invoiceId.isBlank())))
            return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "No orders for checkout");

        if (city == null || city.isBlank() || street == null || street.isBlank() || zip == null || zip.isBlank())
            return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR, null, "No Valid Billing Details");

        final UsersEntity user = userService.extractUserFromRequest(request);

        final List<CartEntity> cartlist = cartRepository.findAllByUser(user);
        System.out.println(cartlist.size()+"Cart List Size=============================");
        if (cartlist.isEmpty()) {
            return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "No orders for checkout");
        }

        final OrdersEntity order = new OrdersEntity();
        order.setCode(invoiceId);
        order.setStatus(OrderStatus.pending);
        order.setDelivery(BigDecimal.valueOf(Delivery_Charges));
        order.setUsersByUsersId(user);
        order.setPaymentMethod(paymentMethod);

        final AddressEntity address = new AddressEntity();
        address.setCity(city);
        address.setStreet(street);
        address.setZipCode(zip);
        address.setOrder(order);



        final List<OrderItemsEntity> orderItems = new ArrayList<>();

        double total = 0;

        for (CartEntity cartItem : cartlist) {
            final OrderItemsEntity item = new OrderItemsEntity();
            item.setOrdersByOrdersId(order);
            item.setStockByStockId(cartItem.getStockByStockId());
            item.setPrice(cartItem.getStockByStockId().getPrice());
            total += cartItem.getStockByStockId().getPrice().doubleValue() * cartItem.getQuantity();
            if (cartItem.getQuantity() > cartItem.getStockByStockId().getQty()) {
                throw new RuntimeException("Not Enough Stock Available");
            }
            item.setQty(cartItem.getQuantity());
            orderItems.add(item);

        }
        order.setOrderItemsById(orderItems);
        System.out.println("ORder Total" + total);
        order.setTotal(BigDecimal.valueOf(total));
        order.setOrderDate(new Date());

        ordersRepository.save(order);
        addressRepository.save(address);

        for (OrderItemsEntity orderItems1 : orderItems) {
            orderItemsRepository.save(orderItems1);
            final StockEntity stock = orderItems1.getStockByStockId();
            stock.setQty(stock.getQty() - orderItems1.getQty());
            stockRepository.update(stock);
        }

        cartRepository.deleteUserCart(user);

        return ResponseUtil.generate(Response.Status.OK, null, "Order Success");
    }

    public Response onCheckout(HttpServletRequest request) {
        final UsersEntity user = userService.extractUserFromRequest(request);
        final List<CartEntity> cartOfUser = cartRepository.findAllByUser(user);

        if (cartOfUser.isEmpty())
            return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Empty Cart Cannot Checkout");

        double total = 0;
        final String merchantSecret = Env.get("payhere.merchant.secret");
        final String merchantId = Env.get("payhere.merchant.id");
        final String invoiceId = "ORD_" + user.getId()+ System.currentTimeMillis() ;

        for (CartEntity cart : cartOfUser) {
            if (cart.getQuantity() > cart.getStockByStockId().getQty())
                return ResponseUtil.generate(Response.Status.INTERNAL_SERVER_ERROR, null, "Cart Quantity cannot be larger than available stock");

            total += (cart.getStockByStockId().getPrice().doubleValue() * cart.getQuantity());
        }

        total += Delivery_Charges;

        System.out.println(total);


        DecimalFormat df = new DecimalFormat("0.00");
        final String amountFormatted = df.format(total);

        final String hash = AppUtil.getMd5(merchantId + invoiceId + amountFormatted + "LKR" + AppUtil.getMd5(merchantSecret));

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("email", user.getEmail());
        map.put("contact", user.getContact());
        map.put("merchantId", merchantId);
        map.put("hash", hash);
        map.put("invoiceId", invoiceId);
        map.put("total", total);
        map.put("currency", "LKR");

        return ResponseUtil.generate(
                Response.Status.OK,
                map,
                "success"
        );


    }

    public OrderDAO getByIdAndUser(Long id, HttpServletRequest request) {
        final UsersEntity user = userService.extractUserFromRequest(request);
        final OrdersEntity order = ordersRepository.findByIdandUser(id, user).orElse(null);
        return generateDAO(order);

    }

    public OrderDAO getByCodeAndUser(String code,HttpServletRequest request){
        final UsersEntity user = userService.extractUserFromRequest(request);
        final OrdersEntity orders = ordersRepository.findByCodeAndUser(code,user).orElse(null);
        return generateDAO(orders);
    }


    public OrderDAO generateDAO(OrdersEntity order) {
        if (order != null) {

            final OrderDAO dao = new OrderDAO();
            dao.setId(order.getId());
            dao.setCode(order.getCode());
            dao.setPaymentMethod(order.getPaymentMethod());
            dao.setDelivery(order.getDelivery());
            dao.setOrderDate(order.getOrderDate());
            dao.setStatus(order.getStatus());
            dao.setTotal(order.getTotal());
            dao.setUserDAO(
                    new UserDAO(
                            order.getUsersByUsersId().getId(),
                            order.getUsersByUsersId().getFirstName(),
                            order.getUsersByUsersId().getLastName(),
                            order.getUsersByUsersId().getEmail(),
                            order.getUsersByUsersId().getContact()
                    )
            );

            final List<OrderItemsDAO> orderItemsDAOS = new ArrayList<>();
            for (OrderItemsEntity item : order.getOrderItemsById()) {
                final OrderItemsDAO itemDAO = new OrderItemsDAO();
                itemDAO.setId(item.getId());
                itemDAO.setPrice(item.getPrice());
                itemDAO.setQty(item.getQty());

                final StockDAO stockDAO = new StockDAO();
                stockDAO.setId(item.getStockByStockId().getId());
                stockDAO.setQty(item.getStockByStockId().getQty());
                stockDAO.setPrice(item.getStockByStockId().getPrice());
                stockDAO.setProductByProductId(
                        new ProductDAO(
                                item.getStockByStockId().getProductByProductId().getId(),
                                item.getStockByStockId().getProductByProductId().getName(),
                                item.getStockByStockId().getProductByProductId().getDescription(),
                                item.getStockByStockId().getProductByProductId().getImages(),
                                new CategoryDAO(
                                        item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getId(),
                                        item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getName(),
                                        item.getStockByStockId().getProductByProductId().getCategoriesByCategoriesId().getDescription()
                                )
                        )
                );

                orderItemsDAOS.add(itemDAO);

            }
            dao.setOrderItems(orderItemsDAOS);

            return dao;

        } else {
            return null;
        }
    }

    public void updateStatus(Long orderId,OrderStatus status,HttpServletRequest request){
        if(orderId == null || status == null || request == null){

            System.out.println("Update Status Failed");
            throw  new ProcessFailedException();

        }else{

            final UsersEntity user = userService.extractUserFromRequest(request);
            final OrdersEntity order = ordersRepository.findByIdandUser(orderId,user).orElseThrow(()->new ValidationFailedException("No Order available"));
            if(status.equals(OrderStatus.canceled) && !order.getStatus().equals(OrderStatus.pending))
                throw new ValidationFailedException("Order status cannot be changed");

            order.setStatus(status);
            update(order);

        }
    }

    public OrderDAO generateShortDTO(OrdersEntity order){
        final OrderDAO dao = new OrderDAO();
        dao.setId(order.getId());
        dao.setCode(order.getCode());
        dao.setPaymentMethod(order.getPaymentMethod());
        dao.setDelivery(order.getDelivery());
        dao.setOrderDate(order.getOrderDate());
        dao.setStatus(order.getStatus());
        dao.setTotal(order.getTotal());
        dao.setUserDAO(
                new UserDAO(
                        order.getUsersByUsersId().getId(),
                        order.getUsersByUsersId().getFirstName(),
                        order.getUsersByUsersId().getLastName()
                )
        );
        return dao;
    }


    public void update(OrdersEntity orders){ordersRepository.update(orders);}

}
