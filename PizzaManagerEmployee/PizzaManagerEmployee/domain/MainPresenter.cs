using PizzaManagerEmployee.data;
using PizzaManagerEmployee.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PizzaManagerEmployee.domain
{
    class MainPresenter
    {
        private IMainView view;

        public MainPresenter(IMainView view)
        {
            this.view = view;
        }

        internal void StartListening()
        {
            Task.Run(() =>
            {
                while (true)
                {
                    RetrieveOrders();
                    Thread.Sleep(1000);
                }
            });
        }

        internal void UpdateOrder(string id, int status)
        {
            // valid interval
            if (status > 0 && status < 4)
            {
                int newStatus = status + 1;
                Task.Run(() => OrderInteractor.UpdateOrder(id, newStatus));
            }
            else
            {
                view.ShowMessage("Oops: Something went wrong!");
            }
        }

        internal void GenerateInvoice(Order selectedOrder)
        {
            IInvoiceWriter writer = new TextInvoiceWriter();
            writer.write(selectedOrder);
        }

        internal void RetrieveOrders()
        {
            var result = OrderInteractor.GetAllOrders();

            if (result == null)
            {
                view.ShowMessage("Connection error!");
            }
            else
            {
                int counter = 0;
                foreach (Order order in result)
                {
                    order.setStatusText();
                    if (order.status == 1)
                    {
                        counter++;
                    }
                }
                view.UpdateOrderCounter(counter);
                view.PopulateDataOrders(result);
            }
        }

    }
}
