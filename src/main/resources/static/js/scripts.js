let selectedCipher = null; // Variável para armazenar o tipo de cifra selecionado

function selectCipher(cipher) {
    selectedCipher = cipher;
    const keyInput = document.getElementById('keyInput');
    const matrixInput = document.getElementById('matrixInput');

    if (cipher === 'hill') {
        keyInput.classList.add('hidden');
        matrixInput.classList.remove('hidden');
    } else {
        keyInput.classList.remove('hidden');
        matrixInput.classList.add('hidden');
    }

    goToStep(3);
}

function goToStep(step) {
    document.querySelectorAll('.step').forEach(div => div.classList.add('hidden'));
    document.getElementById(`step${step}`).classList.remove('hidden');
}

function reset() {
    document.querySelectorAll('.step').forEach(div => div.classList.add('hidden'));
    document.getElementById('step1').classList.remove('hidden');
    document.getElementById('resultado').innerText = '';
    document.getElementById('result').classList.add('hidden');
    selectedCipher = null;
    clearFields(); // Limpa os campos ao resetar
}

async function cifrar() {
    const texto = document.getElementById('texto').value;

    if (!selectedCipher) {
        alert('Please select a cipher type!');
        return;
    }

    let resultado;
    if (selectedCipher === 'hill') {
        const matriz = [
            [parseInt(document.getElementById('m00').value), parseInt(document.getElementById('m01').value)],
            [parseInt(document.getElementById('m10').value), parseInt(document.getElementById('m11').value)]
        ];
        const response = await fetch('/cifras/cifrarHill', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ texto, matriz })
        });
        resultado = await response.text();
    } else {
        const chave = document.getElementById('chave').value;
        const response = await fetch('/cifras/cifrar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ texto, tipo: selectedCipher, chave })
        });
        resultado = await response.text();
    }

    showResult(resultado);
    clearFields(); // Limpa os campos após cifrar
}

async function decifrar() {
    const texto = document.getElementById('texto').value;

    if (!selectedCipher) {
        alert('Please select a cipher type!');
        return;
    }

    let resultado;
    if (selectedCipher === 'hill') {
        const matriz = [
            [parseInt(document.getElementById('m00').value), parseInt(document.getElementById('m01').value)],
            [parseInt(document.getElementById('m10').value), parseInt(document.getElementById('m11').value)]
        ];
        const response = await fetch('/cifras/decifrarHill', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ texto, matriz })
        });
        resultado = await response.text();
    } else {
        const chave = document.getElementById('chave').value;
        const response = await fetch('/cifras/decifrar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ texto, tipo: selectedCipher, chave })
        });
        resultado = await response.text();
    }

    showResult(resultado);
    clearFields(); // Limpa os campos após decifrar
}

function showResult(resultado) {
    document.querySelectorAll('.step').forEach(div => div.classList.add('hidden'));
    document.getElementById('resultado').innerText = resultado;
    document.getElementById('result').classList.remove('hidden');
}

function clearFields() {
    // Limpa os campos de texto e matriz
    document.getElementById('texto').value = '';
    document.getElementById('chave').value = '';
    document.getElementById('m00').value = '';
    document.getElementById('m01').value = '';
    document.getElementById('m10').value = '';
    document.getElementById('m11').value = '';
}