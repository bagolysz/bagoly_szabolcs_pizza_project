using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.model
{
    public class ShoppingCartBindingItem
    {
        public string name { get; set;}
        public double price { get; set; }
        public int quantity { get; set; }
        public string category { get; set; }
    }
}
