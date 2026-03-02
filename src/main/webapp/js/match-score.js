document.addEventListener('DOMContentLoaded', function() {
    const buttons = document.querySelectorAll('.action-button');

    buttons.forEach(btn => {
        btn.addEventListener('click', function(e) {
            if (this.dataset.processing === 'true') {
                e.preventDefault();
                return;
            }

            this.dataset.processing = 'true';

            // Keep the original text
            const originalText = this.textContent;

            // Visual feedback
            this.style.opacity = '0.7';
            this.textContent = '⏳';

            // Safety: If the page doesn't reload after 5 seconds
            // (for example, a network error), return the button to its working state.
            setTimeout(() => {
                if (this.dataset.processing === 'true') {
                    this.dataset.processing = 'false';
                    this.style.opacity = '1';
                    this.textContent = originalText;
                }
            }, 5000);
        });
    });
});