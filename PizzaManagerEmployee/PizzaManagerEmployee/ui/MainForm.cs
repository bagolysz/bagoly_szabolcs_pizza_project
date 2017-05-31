using PizzaManagerEmployee.domain;
using PizzaManagerEmployee.model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PizzaManagerEmployee
{
    public partial class MainForm : Form, IMainView
    {
        private MainPresenter presenter;
        private int selectedIndex = 0;
        private Order selectedOrder;

        public MainForm()
        {
            InitializeComponent();
            dataOrders.AutoGenerateColumns = false;
            dataCart.AutoGenerateColumns = false;
            btnStatus.Enabled = false;
            btnStatus.Text = "None";

            presenter = new MainPresenter(this);

            presenter.StartListening();
        }

        private void btnRefresh_Click(object sender, EventArgs e)
        {
            presenter.RetrieveOrders();
        }

        private void btnInvoice_Click(object sender, EventArgs e)
        {
            if (selectedOrder != null)
            {
                presenter.GenerateInvoice(selectedOrder);
            }
            else
            {
                ShowMessage("Please select an order!");
            }
        }

        private void btnStatus_Click(object sender, EventArgs e)
        {
            if (selectedOrder != null)
            {
                presenter.UpdateOrder(selectedOrder._id, selectedOrder.status);
            }
        }

        private void dataOrders_SelectionChanged(object sender, EventArgs e)
        {
            if (dataOrders.SelectedRows.Count > 0)
            {
                onSelectionChanged();
            }

        }

        private void PopulateCart()
        {
            List<ShoppingCartBindingItem> items = new List<ShoppingCartBindingItem>();
            
            foreach (ShoppingCartItem item in selectedOrder.cart)
            {
                ShoppingCartBindingItem i = new ShoppingCartBindingItem();
                i.name = item.product.title;
                i.price = item.product.price;
                i.quantity = item.quantity;
                switch (item.product.productCategory)
                {
                    case 1:
                        i.category = "Pizza";
                        break;
                    case 2:
                        i.category = "Sandwich";
                        break;
                    case 3:
                        i.category = "Snacks";
                        break;
                    case 4:
                        i.category = "Drinks";
                        break;
                    case 5:
                        i.category = "Promotions";
                        break;
                    default:
                        i.category = "None";
                        break;
                }
                items.Add(i);
            }
           
            dataCart.DataSource = items;
            dataCart.ClearSelection();

            txtAddress.Text = "City: " + selectedOrder.address.city 
                + "\nStreet: " + selectedOrder.address.street 
                + ", " + selectedOrder.address.number
                + "\nMobile phone: " + selectedOrder.address.phoneNumber;
        }


        private void ExecuteSecure(Action a)
        {
            if (InvokeRequired)
                BeginInvoke(a);
            else
                a();
        }
        
        public void ShowMessage(string msg)
        {
            ExecuteSecure(() => MessageBox.Show(msg, "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning));
        }

       
        public void PopulateDataOrders(List<Order> result)
        {
            ExecuteSecure(() =>
            {
                if (dataOrders.CurrentRow != null)
                {
                    selectedIndex = dataOrders.CurrentRow.Index;
                }

                dataOrders.DataSource = result;

                if (result.Count > 0)
                {
                    dataOrders.CurrentCell = dataOrders.Rows[selectedIndex].Cells[0];
                    if (dataOrders.SelectedRows.Count > 0)
                    {
                        onSelectionChanged();
                    }
                }
            });
        }

        private void onSelectionChanged()
        {
            selectedOrder = dataOrders.SelectedRows[0].DataBoundItem as Order;
            PopulateCart();

            switch (selectedOrder.status)
            {
                case 1:
                    btnStatus.Enabled = true;
                    btnStatus.Text = "Prepare order";
                    break;
                case 2:
                    btnStatus.Enabled = true;
                    btnStatus.Text = "Deliver order";
                    break;
                case 3:
                    btnStatus.Enabled = true;
                    btnStatus.Text = "Finish order";
                    break;
                default:
                    btnStatus.Enabled = false;
                    btnStatus.Text = "None";
                    break;
            }
        }

        public void UpdateOrderCounter(int counter)
        {
            lblCounter.Text = Convert.ToString(counter);
        }
    }
}
