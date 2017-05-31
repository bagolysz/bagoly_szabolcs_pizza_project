using PizzaManagerEmployee.model;
using RestSharp;
using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.data
{
    static class OrderInteractor
    {
        public static void UpdateOrder(string id, int status)
        {
            var client = new RestClient(Constants.BASE_URL + "order/" + id);
            var request = new RestRequest(Method.PUT);
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("x-apikey", Constants.API_KEY);
            request.AddHeader("content-type", "application/json");
            request.AddParameter("application/json", "{\"status\":" + status + "}", ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
        }

        public static List<Order> GetAllOrders()
        {
            // get orders
            var clientOrder = new RestClient(Constants.BASE_URL + "order?sort=datetime&dir=-1");
            var request = new RestRequest(Method.GET);
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("x-apikey", Constants.API_KEY);
            request.AddHeader("content-type", "application/json");

            IRestResponse responseOrder = clientOrder.Execute(request);
            RestSharp.Deserializers.JsonDeserializer deserial = new JsonDeserializer();
            List<Order> orders = deserial.Deserialize<List<Order>>(responseOrder);

            // for all orders get address, shopping cart and products
            foreach (Order order in orders)
            {
                // get address
                var clientAddress = new RestClient(Constants.BASE_URL + "address/" + order.addressId);
                IRestResponse responseAddress = clientAddress.Execute(request);
                order.address = deserial.Deserialize<Address>(responseAddress);

                // get shopping cart items
                var clientShoppingCart = new RestClient(Constants.BASE_URL + "cartitem?q={\"orderId\":\"" + order._id + "\"}");
                IRestResponse responseShoppingCart = clientShoppingCart.Execute(request);
                List<ShoppingCartItem> shoppingCart = deserial.Deserialize<List<ShoppingCartItem>>(responseShoppingCart);

                // for each shopping cart item get product
                foreach (ShoppingCartItem item in shoppingCart)
                {
                    // get the product information
                    var clientProduct = new RestClient(Constants.BASE_URL + "products/" + item.productId);
                    IRestResponse responseProduct = clientProduct.Execute(request);
                    item.product = deserial.Deserialize<Product>(responseProduct);

                }
                order.cart = shoppingCart;
            }

            return orders;
        }

    }
}
