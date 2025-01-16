<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="layout" uri="http://callidora.lk/jsp/template-inheritance" %>

<c:if test="${user eq null}">
    <c:redirect url="login"/>
</c:if>

<layout:extends name="base">
    <layout:put block="contents">
        <!-- Single Page Header start -->
        <div class="container-fluid page-header py-5">
            <h1 class="text-center text-white display-6">Cart</h1>
            <ol class="breadcrumb justify-content-center mb-0">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Pages</a></li>
                <li class="breadcrumb-item active text-white">Cart</li>
            </ol>
        </div>
        <!-- Single Page Header End -->


        <!-- Cart Page Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="product-image" scope="col">Products</th>
                            <th class="product-name" scope="col">Name</th>
                            <th class="product-price" scope="col">Price</th>
                            <th class="product-quantity" scope="col">Quantity</th>
                            <th class="product-total" scope="col">Total</th>
                            <th class="product-action" scope="col">Handle</th>
                        </tr>
                        </thead>
                        <tbody id="cart-container">
<%--                                                    <tr>--%>
<%--                                                        <th scope="row">--%>
<%--                                                            <div class="d-flex align-items-center">--%>
<%--                                                                <img src="img/vegetable-item-3.png" class="img-fluid me-5 rounded-circle" style="width: 80px; height: 80px;" alt="">--%>
<%--                                                            </div>--%>
<%--                                                        </th>--%>
<%--                                                        <td>--%>
<%--                                                            <p class="mb-0 mt-4">Big Banana</p>--%>
<%--                                                        </td>--%>
<%--                                                        <td>--%>
<%--                                                            <p class="mb-0 mt-4">2.99 $</p>--%>
<%--                                                        </td>--%>
<%--                                                        <td>--%>
<%--                                                            <div class="input-group quantity mt-4" style="width: 100px;">--%>
<%--                                                                <div class="input-group-btn">--%>
<%--                                                                    <button class="btn btn-sm btn-minus rounded-circle bg-light border" >--%>
<%--                                                                        <i class="fa fa-minus"></i>--%>
<%--                                                                    </button>--%>
<%--                                                                </div>--%>
<%--                                                                <input type="text" class="form-control form-control-sm text-center border-0" value="1">--%>
<%--                                                                <div class="input-group-btn">--%>
<%--                                                                    <button class="btn btn-sm btn-plus rounded-circle bg-light border">--%>
<%--                                                                        <i class="fa fa-plus"></i>--%>
<%--                                                                    </button>--%>
<%--                                                                </div>--%>
<%--                                                            </div>--%>
<%--                                                        </td>--%>
<%--                                                        <td>--%>
<%--                                                            <p class="mb-0 mt-4">2.99 $</p>--%>
<%--                                                        </td>--%>
<%--                                                        <td>--%>
<%--                                                            <button class="btn btn-md rounded-circle bg-light border mt-4" >--%>
<%--                                                                <i class="fa fa-times text-danger"></i>--%>
<%--                                                            </button>--%>
<%--                                                        </td>--%>

<%--                                                    </tr>--%>
                        </tbody>
                    </table>
                </div>
                    <%--                <div class="mt-5">--%>
                    <%--                    <input type="text" class="border-0 border-bottom rounded me-5 py-3 mb-4" placeholder="Coupon Code">--%>
                    <%--                    <button class="btn border-secondary rounded-pill px-4 py-3 text-primary" type="button">Apply Coupon</button>--%>
                    <%--                </div>--%>






                <div class="row mt-6 g-4 justify-content-end">
<%--                    <div class="col-8"></div>--%>

                    <div class="col-sm-4 col-md-5 col-lg-6 col-xl-8 sticky-sidebar-wrapper">
                        <div class="sticky-sidebar">
                            <div class="cart-summary mb-4">
                                <h3 class="cart-title text-uppercase">Invoice Details</h3>
                                    <%--                                    <div class="cart-subtotal d-flex align-items-center justify-content-between">--%>
                                    <%--                                        <label class="ls-25">Subtotal</label>--%>
                                    <%--                                        <span>$100.00</span>--%>
                                    <%--                                    </div>--%>

                                <hr class="divider">

                                <ul class="shipping-methods mb-2">
                                    <li>
                                        <label
                                                class="shipping-title text-dark font-weight-bold">Payment
                                            Method</label>
                                    </li>
                                    <li>
                                        <div class="custom-radio mt-2">
                                            <input type="radio" id="card-payment" class="custom-control-input"
                                                   name="pay" value="card" checked>
                                            <label for="card-payment" class="custom-control-label color-dark">
                                                Card Payment
                                            </label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="custom-radio mt-2">
                                            <input type="radio" id="cod" class="custom-control-input"
                                                   name="pay" value="cod">
                                            <label for="cod" class="custom-control-label color-dark">
                                                Cash On Delivery
                                            </label>
                                        </div>
                                    </li>
                                </ul>

                                <div class="shipping-calculator mt-4">
                                    <p class="shipping-destination lh-1">Shipping to <strong>LK</strong>.</p>

                                    <div class="shipping-calculator-form">
                                        <div class="form-group mt-3">
                                            <input class="form-control form-control-md" type="text"
                                                   id="cityElement" placeholder="Town / City">
                                        </div>
                                        <div class="form-group mt-3">
                                            <input class="form-control form-control-md" type="text"
                                                   id="streetElement" placeholder="Street">
                                        </div>
                                        <div class="form-group mt-3">
                                            <input class="form-control form-control-md" type="text"
                                                   id="zipcodeElement" placeholder="ZIP">
                                        </div>
                                            <%--                                            <button type="submit" class="btn btn-dark btn-outline btn-rounded">Update--%>
                                            <%--                                                Totals--%>
                                            <%--                                            </button>--%>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>


                    <div class="col-sm-8 col-md-7 col-lg-6 col-xl-4">
                        <div class="bg-light rounded">
                            <div class="p-4">
                                <h1 class="display-6 mb-4">Cart <span class="fw-normal">Total</span></h1>
                                <div class="d-flex justify-content-between mb-4">
                                    <h5 class="mb-0 me-4">Subtotal:</h5>
                                    <p class="mb-0" id="subTotal">LKR 0.00</p>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <h5 class="mb-0 me-4">Shipping</h5>
                                    <div class="">
                                        <p class="mb-0" id="deliveryCost">LKR 0.00</p>
                                    </div>
                                </div>
                            </div>
                            <div class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
                                <h5 class="mb-0 ps-4 me-4">Total</h5>
                                <p class="mb-0 pe-4 " id="finalTotal">LKR 0.00</p>
                            </div>
                            <button class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4"
                                    type="button" onclick="onClickCheckout()">Proceed Checkout
                            </button>

<%--                            <button class="btn border-dark rounded-pill px-4 py-3 text-dark text-uppercase mb-4 ms-4"--%>
<%--                                    type="button">Save Order--%>
<%--                            </button>--%>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Cart Page End -->

    </layout:put>
    <layout:put block="script">

        <script type="text/javascript" src="https://www.payhere.lk/lib/payhere.js"></script>
        <script type="text/javascript">


            fetchCartData();

            function fetchCartData() {
                secureFetch("${BASE_URL}api/v1/cart")
                    .then(async response => {
                        if (response.ok) {
                            return await response.json();
                        } else
                            return null;
                    })
                    .then(
                        async data => {
                            if (data) {
                                listCartItems(data?.data);
                                calculateTotals(data?.data)
                            }else
                                window.location.href="${BASE_URL}login";
                        }

                    );
            }


            function listCartItems(data) {
                const table = document.getElementById("cart-container");
                table.innerHTML = "";
                if (data?.length > 0) {
                    data.forEach(item => {
                        let row = table.insertRow();
                        let cell = row.insertCell();
                        let productPrice = item.stockDAO?.price ?? 0;
                        let qty = item?.quantity ?? 0;
                        let cartId = item?.id;


                        cell.classList.add("product-image");

                        cell.innerHTML = `
                         <p class="mb-0 mt-4">
                                   ` + (item?.id) + `
                         </p>
                        `;
                        cell = row.insertCell();
                        cell.classList.add("product-name");
                        cell.innerHTML = `
                            <p class="mb-0 mt-4">` + (item?.stockDAO?.productByProductId?.name) + `</p>
                        `;
                        cell = row.insertCell();
                        cell.classList.add("product-price");
                        cell.innerHTML = `
                         <p class="mb-0 mt-4">`+(parseFloat(item?.stockDAO?.price).toFixed(2))+`</p>
                        `;

                        cell = row.insertCell();
                        cell.classList.add("product-quantity");
                        cell.innerHTML = `
                                <div class="input-group quantity mt-4" style="width: 100px;">
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-minus rounded-circle bg-light border" onclick="minusQuantity(` + item.id + `,` + qty + `)">
                                            <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <input type="text" class="form-control form-control-sm text-center border-0" value="`+Number(qty)+`" disabled>
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-plus rounded-circle bg-light border" onclick="plusQuantity(` + item.id + `,` + qty + `);">
                                            <i class="fa fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                        `;

                        cell = row.insertCell();
                        cell.classList.add("product-total");
                        cell.innerHTML = `
                                <p class="mb-0 mt-4">LKR `+parseFloat(item?.stockDAO?.price*qty).toFixed(2)+`</p>
                        `;

                        cell = row.insertCell();
                        cell.classList.add("product-action");
                        cell.innerHTML = `
                         <button class="btn btn-md rounded-circle bg-light border mt-4" onclick="removeItemFromCart(`+item.id+`);">
                                                                <i class="fa fa-times text-danger"></i>
                                                            </button>
                        `

                    });

                }else{
                    let row = table.insertRow();
                    let cell = row.insertCell();
                    cell.classList.add("product-name");
                    cell.classList.add("text-center");
                    cell.colSpan = 4;
                    cell.innerHTML= "No Cart Items Available";
                }
            }

            function calculateTotals(data){
                const subTotalElement = document.getElementById("subTotal");
                const deliveryCostElement = document.getElementById("deliveryCost");
                const total = document.getElementById("finalTotal");

                const DELIVERY_COST = 600;


                let subTotal = 0;
                let finalTotal = 0;


                if(data?.length){
                    data.forEach(item=>{
                        let qty = item?.quantity;
                        subTotal += (item?.stockDAO?.price ?? 0) * qty;
                        finalTotal += (item?.stockDAO?.price ? (item?.stockDAO?.price * qty): 0);
                    });

                    finalTotal+= DELIVERY_COST;

                    const CURRENCY_TAG = "Rs. ";

                    subTotalElement.innerHTML = CURRENCY_TAG+parseFloat(subTotal).toFixed(2);
                    deliveryCostElement.innerHTML = CURRENCY_TAG + parseFloat(DELIVERY_COST).toFixed(2);
                    total.innerHTML = CURRENCY_TAG + parseFloat(finalTotal).toFixed(2);


                }else{
                    subTotalElement.innerHTML = "";
                    deliveryCostElement.innerHTML = "";
                    total.innerHTML = "";
                }

            }

            function onClickCheckout(){
                const city = document.getElementById("cityElement").value;
                const street = document.getElementById("streetElement").value;
                const zipcode = document.getElementById("zipcodeElement").value;
                const paymentMethod = document.querySelector("input[name='pay']:checked").value;
                console.log(paymentMethod);

                if(!city || !city.trim()|| !street||!street.trim()||!zipcode||!zipcode.trim()){
                    alert("shipping adress is compulsory to fill  in");
                }else{

                    const addressDetails = {
                        city,
                        street,
                        zipcode,
                        paymentMethod
                    };


                    secureFetch("${BASE_URL}api/v1/orders/cart/on-checkout")
                        .then(async response => {
                            if (response.ok) {
                                return await response.json();
                            } else
                                return null;
                        })
                        .then(async data => {
                            if (data) {
                                if (paymentMethod === "card")
                                    initCardPayment(data?.data ?? null, addressDetails);
                                else
                                    saveCartOrder(data?.data ?? null, addressDetails);
                            } else {
                                alert("Checkout failed");
                            }
                        });

                }




            }


            function initCardPayment(data, addressDetails) {
                if (data && addressDetails) {
                    alert(data.hash);
                    console.log(data);
                    console.log(addressDetails);

                    payhere.onCompleted = function onCompleted(orderId) {
                        console.log("Payment completed. OrderID:" + orderId);

                        saveCartOrder(data, addressDetails);
                    };

                    // Payment window closed
                    payhere.onDismissed = function onDismissed() {
                        // Note: Prompt user to pay again or show an error page
                        console.log("Payment dismissed");
                        window.location.href = "${BASE_URL}cart";
                    };

                    // Error occurred
                    payhere.onError = function onError(error) {
                        // Note: show an error page
                        console.log("Error:" + error);
                        alert("Process failed try again later.")
                    };

                    // Put the payment variables here
                    let payment = {
                        "sandbox": true,
                        "merchant_id": data.merchantId ?? "",    // Replace your Merchant ID
                        "return_url": "",     // Important
                        "cancel_url": "",     // Important
                        "notify_url": "",
                        "order_id": data.invoiceId ?? "",
                        "items": "Cart",
                        "amount": parseFloat(String(data.total ?? 0)).toFixed(2),
                        "currency": data.currency ?? "",
                        "hash": data.hash ?? "", // *Replace with generated hash retrieved from backend
                        "first_name": data.firstName ?? "",
                        "last_name": data.lastName ?? "",
                        "email": data.email ?? "",
                        "phone": data.contact ?? "",
                        "address": addressDetails.street ?? "",
                        "city": addressDetails.city ?? "",
                        "country": "Sri Lanka",
                        "delivery_address": addressDetails.street ?? "",
                        "delivery_city": addressDetails.city ?? "",
                        "delivery_country": "Sri Lanka",
                        "custom_1": "",
                        "custom_2": ""
                    };

                    console.log(payment);

                    // Show the payhere.js popup, when "PayHere Pay" is clicked
                    // document.getElementById('payhere-payment').onclick = function (e) {
                    payhere.startPayment(payment);
                    // };

                } else {
                    alert("Missing Data on payment");
                }
            }


            function saveCartOrder(paymentData,addressDetails){
                if(paymentData && addressDetails){
                    let formData =  new FormData();
                    formData.append("invoiceId",paymentData.invoiceId ?? "");
                    formData.append("payment_method",addressDetails.paymentMethod ?? null);
                    formData.append("city",addressDetails.city);
                    formData.append("street",addressDetails.street);
                    formData.append("zipCode",addressDetails.zipcode);

                    secureFetch("${BASE_URL}api/v1/orders/cart-checkout",{
                        method:'post',
                        headers:'multipart/form-data',
                        body:formData
                    }).then(
                        async response =>{
                            if(response.ok){
                                return await response.json();
                            }else{
                                return null;
                            }
                        })
                        .then(async data=>{
                            if(data){
                                console.log(JSON.stringify(data));
                                alert("Order Placed Successfully");
                                window.location.href = "${BASE_URL}";
                            }else{
                                alert("couldn't save Order Data");
                                window.location.href = "${BASE_URL}cart";
                            }
                        })

                }else
                    alert("Invalid Command");
            }

            function plusQuantity(itemId, quantity) {
                if (itemId && quantity && quantity) {
                    // alert("plus");
                    updateQuantity(itemId, Number(quantity) + 1);
                }
            }

            function minusQuantity(itemId, quantity) {
                if (itemId && quantity && quantity > 1) {
                    // alert("minus");
                    updateQuantity(itemId, Number(quantity) - 1);
                }
            }

            function updateQuantity(itemId, quantity) {
                const data = {
                    id: itemId,
                    quantity
                }
                console.log("REQUEST BODY :::", data);
                secureFetch("${BASE_URL}api/v1/cart/qty", {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                    .then(async response => {
                        if (response.ok) {
                            return await response.json();
                        }
                        return null;
                    })
                    .then(async data => {
                        if (data)
                            fetchCartData();
                        else
                            alert("Quantity update failed");
                    });
            }

            function removeItemFromCart(itemId){
                if(itemId){
                    secureFetch("${BASE_URL}api/v1/cart"+itemId,{
                        method: 'DELETE',
                    })
                        .then(async response =>{
                            if(response.ok){
                                return await response.json();
                            }
                            return null;
                        })
                        .then(async data=>{

                            if(data){
                                fetchCartData();
                            }else{
                                alert("Cart Removal Failed");
                            }

                        });
                }
            }

            function clearMyCart(){
                secureFetch("${BASE_URL}api/v1/clear-my-cart",{
                    method:'DELETE'
                })
                    .then(async response=>{
                        if(response.ok)
                            return await response.json();

                        return null;
                    }).then(async data=>{
                        if(data)
                            fetchCartData();
                        else
                            alert("Cart Clear Failed");
                });
            }



        </script>


    </layout:put>

</layout:extends>