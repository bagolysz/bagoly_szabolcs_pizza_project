using PizzaManagerEmployee.model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.data
{
    class TextInvoiceWriter : IInvoiceWriter
    {
        public void write(Order order)
        {
            string filename = order._id + "_" +  DateTime.UtcNow.ToString("dd-MM-yyyy") + "_Invoice.txt";

            using (StreamWriter writer = new StreamWriter(filename))
            {
                writer.WriteLine("Invoice generated for order: " + order._id);
                writer.WriteLine("Delivery address: " + order.address.city
                                                      + ", " + order.address.street + ", " + order.address.number);
                writer.WriteLine("Phone number: " + order.address.phoneNumber);
                writer.WriteLine(" ");
                writer.WriteLine("Products:");

                for (int i = 0; i < order.cart.Count; i++)
                {
                    Product p = order.cart.ElementAt(i).product;
                    writer.WriteLine("No." + (i + 1) + " -- " + p.title + " " + p.price + " RON -- x" + order.cart.ElementAt(i).quantity);
                }

                writer.WriteLine(" ");
                writer.WriteLine("Total: " + order.total + " RON");

                writer.WriteLine(" ");
                writer.WriteLine("Invoice generated at: " + String.Format("{0:G}", DateTime.Now));
            }
        }
    }
}
