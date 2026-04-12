

function startPaymentSimulation() {

    const overlay = document.getElementById('paymentOverlay');
    const processing = document.getElementById('processingState');
    const success = document.getElementById('successState');
    const checkmark = document.querySelector('.success-checkmark');
    
   
    const fullName = document.getElementById('fullName').value;
    const addressLine1 = document.getElementById('addressLine1').value;
    const city = document.getElementById('city').value;
    const zipCode = document.getElementById('zipCode').value;

    const isCartEmpty = document.querySelectorAll('.cart-item').length === 0;

    
    if (isCartEmpty) {
        alert("Your cart is empty. Please add items before proceeding.");
        return;
    }

    if (!fullName || !addressLine1 || !city || !zipCode) {
        alert("Please complete all shipping address fields before proceeding to checkout.");
        return;
    }

   
    processing.style.display = 'block';
    success.style.display = 'none';
    checkmark.classList.remove('show');
    
    overlay.style.display = 'flex';
   
    setTimeout(() => overlay.classList.add('show'), 10); 

    
    setTimeout(() => {
     
        processing.style.display = 'none';
        success.style.display = 'block';
        
     
        checkmark.classList.add('show');

    
        document.getElementById('shippingForm').reset();
        
    }, 2000); 
}
