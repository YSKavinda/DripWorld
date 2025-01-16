<%@taglib prefix="layout" uri="http://callidora.lk/jsp/template-inheritance" %>

<layout:extends name="base">
    <layout:put block="contents">

        <!-- Single Page Header start -->
        <div class="container-fluid page-header py-5">
            <h1 class="text-center text-white display-6">Shop Detail</h1>
            <ol class="breadcrumb justify-content-center mb-0">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Pages</a></li>
                <li class="breadcrumb-item active text-white">Shop Detail</li>
            </ol>
        </div>
        <!-- Single Page Header End -->


        <!-- Single Product Start -->
        <div class="container-fluid py-5 mt-5">
            <div class="container py-5">
                <div class="row g-4 mb-5">
                    <div class="col-lg-10 offset-1" id="itemId">


                    </div>

                        <%--          <div class="col-lg-4 col-xl-3">--%>
                        <%--            <div class="row g-4 fruite">--%>
                        <%--              <div class="col-lg-12">--%>
                        <%--                <div class="input-group w-100 mx-auto d-flex mb-4">--%>
                        <%--                  <input type="search" class="form-control p-3" placeholder="keywords" aria-describedby="search-icon-1">--%>
                        <%--                  <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>--%>
                        <%--                </div>--%>
                        <%--                <div class="mb-4">--%>
                        <%--                  <h4>Categories</h4>--%>
                        <%--                  <ul class="list-unstyled fruite-categorie">--%>
                        <%--                    <li>--%>
                        <%--                      <div class="d-flex justify-content-between fruite-name">--%>
                        <%--                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Apples</a>--%>
                        <%--                        <span>(3)</span>--%>
                        <%--                      </div>--%>
                        <%--                    </li>--%>
                        <%--                    <li>--%>
                        <%--                      <div class="d-flex justify-content-between fruite-name">--%>
                        <%--                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Oranges</a>--%>
                        <%--                        <span>(5)</span>--%>
                        <%--                      </div>--%>
                        <%--                    </li>--%>
                        <%--                    <li>--%>
                        <%--                      <div class="d-flex justify-content-between fruite-name">--%>
                        <%--                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Strawbery</a>--%>
                        <%--                        <span>(2)</span>--%>
                        <%--                      </div>--%>
                        <%--                    </li>--%>
                        <%--                    <li>--%>
                        <%--                      <div class="d-flex justify-content-between fruite-name">--%>
                        <%--                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Banana</a>--%>
                        <%--                        <span>(8)</span>--%>
                        <%--                      </div>--%>
                        <%--                    </li>--%>
                        <%--                    <li>--%>
                        <%--                      <div class="d-flex justify-content-between fruite-name">--%>
                        <%--                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Pumpkin</a>--%>
                        <%--                        <span>(5)</span>--%>
                        <%--                      </div>--%>
                        <%--                    </li>--%>
                        <%--                  </ul>--%>
                        <%--                </div>--%>
                        <%--              </div>--%>
                        <%--            </div>--%>
                        <%--          </div>--%>
                </div>


            </div>
        </div>
        <!-- Single Product End -->

    </layout:put>
    <layout:put block="script">
        <script type="text/javascript">
            const id = ${model};
            // const stock_qty = 1;
            // let qty = 1;
            // const qtyElement = document.getElementById("qty-field");
            <%--alert(${model});--%>

            loadItemData();

            async function loadItemData() {
                const itemContainer = document.getElementById("itemId");

                await fetch("${BASE_URL}api/v1/stock/single-product?stock_id=" + id)
                    .then(async resp => {

                        if (resp.ok) {
                            return await resp.json();
                        }

                        return null;

                    }).then(async data => {

                        console.log(data?.data);
                        // if (data?.data?.length > 0) {

                        if (data?.data?.id) {
                            const item = data?.data;

                            const element = `<div class="row g-4">
              <div class="col-lg-6">
                <div class="border rounded">
                  <a href="#">
                   ` + (item?.productByProductId?.images?.[0] ? '<img src="${BASE_URL}' + item?.productByProductId?.images?.[0] + '" class="img-fluid rounded" alt="Stock"/>' : '') + `
                  </a>
                </div>
              </div>

              <div class="col-lg-6">
                <h4 class="fw-bold mb-3">` + (item?.productByProductId?.name) + `</h4>
                <p class="mb-3">Category: ` + (item?.productByProductId?.categoriesByCategoriesId?.name) + `</p>
                <h5 class="fw-bold mb-3">Rs.` + (item?.price) + `/ kg</h5>

                <p class="mb-4">` + (item?.productByProductId?.description) + ' ' + (item?.productByProductId?.categoriesByCategoriesId?.name) + `</p>
                <div class="input-group quantity mb-5" style="width: 200px;">
                  <span class="form-label text-center border-0">Available Stocks:</span>
                  <span class="form-label text-center fw-bold border-0">&nbsp;&nbsp;`+(item?.qty)+`</span>

                </div>
                <a href="#" class="btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary"><i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart</a>
              </div>


            </div>
`;

                            itemContainer.innerHTML = element;


                        }


                        // }

                    });

                }



        </script>
    </layout:put>
</layout:extends>

