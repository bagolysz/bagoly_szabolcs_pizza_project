using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.model
{
    public class ShoppingCartItem
    {
        public string _id { get; set;}
        public string productId { get; set;}
        public string orderId { get; set; }
        public int quantity { get; set; }

        public Product product { get; set; }
    }
}
