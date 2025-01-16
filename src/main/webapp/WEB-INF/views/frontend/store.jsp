<%@taglib prefix="layout" uri="http://callidora.lk/jsp/template-inheritance" %>

<layout:extends name="base">
    <layout:put block="contents">
        <!-- Single Page Header start -->
        <div class="container-fluid page-header py-5">
            <h1 class="text-center text-white display-6">Shop</h1>
            <ol class="breadcrumb justify-content-center mb-0">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Pages</a></li>
                <li class="breadcrumb-item active text-white">Shop</li>
            </ol>
        </div>
        <!-- Single Page Header End -->
        <!-- Fruits Shop Start-->
        <div class="container-fluid fruite py-5">
            <div class="container py-5">
                <h1 class="mb-4">Product Catalogue</h1>
                <div class="row g-4">
                    <div class="col-lg-12">
                        <div class="row g-4">
                            <div class="col-xl-3">
                                <div class="input-group w-100 mx-auto d-flex">
                                    <input type="search" class="form-control p-3" placeholder="keywords"
                                           aria-describedby="search-icon-1" id="search-key" onkeyup="onKeywordChanges();">
                                    <span id="search-icon-1" class="input-group-text p-3" onclick="setKeywords()"><i
                                            class="fa fa-search"></i></span>
                                </div>
                            </div>
                            <div class="col-6"></div>
                            <div class="col-xl-3">
                                <div class="bg-light ps-3 py-3 rounded d-flex justify-content-between mb-4">
                                    <label for="filterFormatType">Default Sorting:</label>
                                    <select name="orderby" class="border-0 form-select-sm bg-light me-3" id="filterFormatType"
                                            onchange="onChangeFilters()">
                                        <option value=null selected="selected">-Select-</option>
                                        <option value="A_Z">A to Z</option>
                                        <option value="Z_A">Z to A</option>
                                        <option value="LATEST">Latest</option>
                                        <option value="L_H">Price: low to high</option>
                                        <option value="H_L">Price: high to low</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row g-4">
                            <div class="col-lg-3">
                                <div class="row g-4">
                                    <div class="col-lg-12">
                                        <div class="mt-5 mb-3">
                                            <h4>Categories</h4>
                                            <ul class="list-unstyled fruite-categorie" id="categoryList">
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Apples</a>
                                                        <span>(3)</span>
                                                    </div>
                                                </li>

                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-9">
                                <div class="row g-4 justify-content-center" id="productList">
                                        <%--product-card--%>
                                    <div class="col-md-6 col-lg-6 col-xl-4">
                                        <div class="rounded position-relative fruite-item">
                                            <div class="fruite-img">
                                                <img src="img/fruite-item-5.jpg" class="img-fluid w-100 rounded-top"
                                                     alt="">
                                            </div>
                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                 style="top: 10px; left: 10px;">Fruits
                                            </div>
                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                <h4>Grapes</h4>
                                                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit sed do
                                                    eiusmod te incididunt</p>
                                                <div class="d-flex justify-content-between flex-lg-wrap">
                                                    <p class="text-dark fs-5 fw-bold mb-0">$4.99 / kg</p>
                                                    <a href="#"
                                                       class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                            class="fa fa-shopping-bag me-2 text-primary"></i> Add to
                                                        cart</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                        <%--product-card--%>



                                </div>
                                <div class="row g-4 justify-content-center">
                                    <div class="col-12">
                                        <div class="pagination d-flex justify-content-center mt-5" id="pagination-box">
<%--                                            <a href="#" class="rounded">&laquo;</a>--%>
<%--                                            <a href="#" class="active rounded">1</a>--%>
<%--                                            <a href="#" class="rounded">2</a>--%>
<%--                                            <a href="#" class="rounded">3</a>--%>
<%--                                            <a href="#" class="rounded">4</a>--%>
<%--                                            <a href="#" class="rounded">5</a>--%>
<%--                                            <a href="#" class="rounded">6</a>--%>
<%--                                            <a href="#" class="rounded">&raquo;</a>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fruits Shop End-->
    </layout:put>
    <layout:put block="script" type="REPLACE">
        <script type="text/javascript">
            let totalPageCount = 0;
            let totalElements = 0;
            let keywords = "";
            let maxPrice = 0;
            let selectedCategory = null;
            let selectBrand = null;
            let resultSorter = null;
            let pageSize = 6;
            let pageNo = 1;


            function openSingleProductView(id){
               const url = "${BASE_URL}single-product/{stock_id}";
               window.location.href = url.replace("{stock_id}",id);
            }


            const setKeywords = () => {
                keywords = document.getElementById("search-key").value;;
                search();
            }

            const setSelectedCategory = (id)=>{
               selectedCategory = id == "null" ? null :id;
               document.getElementById("categoryList").value = selectedCategory;
               console.log(selectedCategory);
               search();
            }
            const setSorting = (order)=>{
                resultSorter = order == "null"?null:order;
                search();
            }
            const setPageNo = (number) => {
                pageNo = Number(number);
                search();
            }



            loadCategories();
            search();


            async function loadCategories() {
                const categoryList = document.getElementById("categoryList");

                categoryList.innerHTML = "";

                categoryList.innerHTML += "<li> <div class="+"d-flex justify-content-between fruite-name"+">" +
                    "                                    <a href='javascript:;' onclick='onChangeSelectedCategory("+"null"+",false)'><i class="+"fas fa-apple-alt me-2"+"></i>All Categories</a> </div></li>";

                await fetch("${BASE_URL}api/v1/categories").then(async resp =>{
                    if(resp.ok){
                        return await resp.json();
                    }
                    return null;
                }).then(async data=>{
                    if(data){
                        if(data?.data?.length > 0){
                            data.data.map((category)=>{
                                categoryList.innerHTML += "<li> <div class="+"d-flex justify-content-between fruite-name"+">" +
                                    "                                    <a href='javascript:;' onclick='onChangeSelectedCategory("+category.id+",false)'><i class="+"fas fa-apple-alt me-2"+"></i>"+category?.name ?? '' + "</a>";
                            });
                        }else{
                            console.log("Empty Category List");
                        }
                    }else {
                        console.log("Error Loading Data");
                    }
                })

            }

            async function search(){
                const productList = document.getElementById("productList");
                productList.innerHTML = "";
                let query = "${BASE_URL}api/v1/stock/search?pageNo="+pageNo+"&pageSize="+pageSize;

                if(keywords)
                    query += "&key="+keywords;
                if(selectedCategory)
                    query += "&category="+selectedCategory;
                if(resultSorter)
                    query += "&sortBy="+resultSorter;

                console.log(query);
                await fetch(query)
                    .then(async response=>{
                       if(response.ok){
                           return await response.json();
                       }
                        return null;
                    })
                    .then(async data=>{
                        if(data?.data?.length>0){
                            totalPageCount = data?.totalPages;
                            data.data.map((item)=>{

                                const cardTemplate = ` <div class="col-md-6 col-lg-6 col-xl-4" onClick=" openSingleProductView(`+item?.id+`)">
                                        <div class="rounded position-relative fruite-item">
                                            <div class="fruite-img">
                                                  `+(item?.productByProductId?.images?.[0]? '<img src="${BASE_URL}'+item?.productByProductId?.images?.[0]+'" class="img-fluid w-100 rounded-top" alt="Stock"/>':'')+`
                                            </div>
                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                 style="top: 10px; left: 10px;">`+(item?.productByProductId?.categoriesByCategoriesId?.name)+`
                                            </div>
                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                <h4>`+(item?.productByProductId?.name)+`</h4>
                                                <p>`+(item?.productByProductId?.description)+' '+(item?.productByProductId?.categoriesByCategoriesId?.name)+`</p>
                                                <div class="d-flex justify-content-between flex-lg-wrap">
                                                    <p class="text-dark fs-5 fw-bold mb-0">Rs.`+(item?.price)+`/ kg</p>
                                                    <a href="javascript:;" onclick="addToCart(`+item.id+`)"
                                                       class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                            class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`;
                                productList.innerHTML += cardTemplate;
                            });

                            setupPagination(data?.totalPages ?? 0, data?.totalElements ?? 0);
                        }else
                            totalPageCount = 0;
                    });
            }


            function setupPagination(totalPages = 0, elementCount = 0){
                const page_number_box = document.getElementById("pagination-box");
                page_number_box.innerHTML =  "";

                let view = "";
                if(totalPages>0 && elementCount > 0){
                    if(pageNo>1){
                        view +=
                            `
                             <a href="#" class="rounded" onclick="decrementPageNo();" ">&laquo;</a>
                            `
                    }
                    for(let i = 1; i<= totalPages; i++){
                        view += `
                           <a href="#" class="`+(pageNo == i ? 'active':'')+` rounded" href="javascript:;" onclick="setPageNo(`+i+`)">`+i+`</a>
                        `;
                    }
                    if(pageNo<totalPages){
                        view +=
                            `
                             <a href="#" class="rounded" onclick="incrementPageNo();">&raquo;</a>
                            `
                    }


                    page_number_box.innerHTML =  view ;

                }

            }

            function addToCart(id){
                const data = {
                   stockId:id,
                    quantity:1,
                };
                secureFetch("${BASE_URL}api/v1/cart/add",{
                    method: 'post',
                    headers:{
                        'Content-Type':'application/json'
                    },
                    body:JSON.stringify(data)
                }).then(async response=>{
                    if(response.ok){
                        return await response.json();
                    }
                    return null;
                })
                    .then(async data =>{
                        if(data){
                            alert("Item added to Cart");
                            window.location.href="${BASE_URL}cart";
                        }else{
                            alert("Failed to Add to Cart");
                        }
                    });
            }



            const onChangeSelectedCategory = (element, isElement = true) => {
                setSelectedCategory(isElement ? document.getElementById(element).value : element);
            }

            const onKeywordChanges = ()=>{
                setKeywords(document.getElementById("search-key").value ?? null);
            }

            const onChangeSorting = ()=>{
                setSorting(document.getElementById("filterFormatType").value);
            }

            const incrementPageNo = () => {
                if (pageNo < totalPageCount)
                    pageNo++;
                search();
            }
            const decrementPageNo = () => {
                if (pageNo > 1)
                    pageNo--;
                search();
            }



        </script>
    </layout:put>
</layout:extends>

