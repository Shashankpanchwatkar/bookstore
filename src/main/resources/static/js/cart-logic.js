// This script handles client-side validation and the payment simulation animation.

function startPaymentSimulation() {
    // 1. Get DOM elements
    const overlay = document.getElementById('paymentOverlay');
    const processing = document.getElementById('processingState');
    const success = document.getElementById('successState');
    const checkmark = document.querySelector('.success-checkmark');
    
    // 2. Client-side validation for address fields
    const fullName = document.getElementById('fullName').value;
    const addressLine1 = document.getElementById('addressLine1').value;
    const city = document.getElementById('city').value;
    const zipCode = document.getElementById('zipCode').value;

    const isCartEmpty = document.querySelectorAll('.cart-item').length === 0;

    // NOTE: For a real application, you would dynamically fetch the subtotal from the model.
    // Here we use a check to ensure we have items.
    if (isCartEmpty) {
        alert("Your cart is empty. Please add items before proceeding.");
        return;
    }

    if (!fullName || !addressLine1 || !city || !zipCode) {
        alert("Please complete all shipping address fields before proceeding to checkout.");
        return;
    }

    // --- Start Animation Sequence ---

    // Reset state and show overlay
    processing.style.display = 'block';
    success.style.display = 'none';
    checkmark.classList.remove('show');
    
    overlay.style.display = 'flex';
    // Use a small delay to ensure CSS display property registers before starting transition
    setTimeout(() => overlay.classList.add('show'), 10); 

    // 3. Simulate payment processing delay (2 seconds)
    setTimeout(() => {
        // 4. Switch to success state
        processing.style.display = 'none';
        success.style.display = 'block';
        
        // 5. Trigger success animation
        checkmark.classList.add('show');

        // Optional: Clear shipping form data after simulated success
        document.getElementById('shippingForm').reset();
        
    }, 2000); 
}
