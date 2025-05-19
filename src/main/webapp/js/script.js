console.log('JS is working!'); // Checking the connection

// Field highlighting when focusing
document.querySelectorAll('input').forEach(input => {
    input.addEventListener('focus', () => {
        input.style.backgroundColor = '#fffde7';
    });
    input.addEventListener('blur', () => {
        input.style.backgroundColor = 'white';
    });
});