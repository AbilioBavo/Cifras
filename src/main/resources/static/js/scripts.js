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
            const texto = document.getElementById('texto').value.trim();

            if (!texto) {
                showResult('Por favor, digite um texto para cifrar', true);
                return;
            }

            if (!selectedCipher) {
                showResult('Por favor, selecione um tipo de cifra!', true);
                return;
            }

            showLoading(true);

            let resultado;
            let erro = false;
            
            try {
                if (selectedCipher === 'hill') {
                    const m00 = parseInt(document.getElementById('m00').value);
                    const m01 = parseInt(document.getElementById('m01').value);
                    const m10 = parseInt(document.getElementById('m10').value);
                    const m11 = parseInt(document.getElementById('m11').value);
                    
                    if (isNaN(m00) || isNaN(m01) || isNaN(m10) || isNaN(m11)) {
                        showResult('Por favor, preencha todos os campos da matriz com números válidos', true);
                        return;
                    }
                    
                    const matriz = [[m00, m01], [m10, m11]];
                    const response = await fetch('/cifras/cifrarHill', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ texto, matriz })
                    });
                    resultado = await response.text();
                    if (!response.ok) erro = true;
                } else {
                    const chave = document.getElementById('chave').value.trim();
                    if (!chave) {
                        showResult('Por favor, digite uma chave válida', true);
                        return;
                    }
                    
                    const response = await fetch('/cifras/cifrar', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ texto, tipo: selectedCipher, chave })
                    });
                    resultado = await response.text();
                    if (!response.ok) erro = true;
                }
            } catch (e) {
                resultado = 'Erro de comunicação com o servidor. Por favor, tente novamente.';
                erro = true;
            } finally {
                showLoading(false);
            }

            showResult(resultado, erro);
        }

        async function decifrar() {
            const texto = document.getElementById('texto').value.trim();

            if (!texto) {
                showResult('Por favor, digite um texto para decifrar', true);
                return;
            }

            if (!selectedCipher) {
                showResult('Por favor, selecione um tipo de cifra!', true);
                return;
            }

            showLoading(true);

            let resultado;
            let erro = false;
            
            try {
                if (selectedCipher === 'hill') {
                    const m00 = parseInt(document.getElementById('m00').value);
                    const m01 = parseInt(document.getElementById('m01').value);
                    const m10 = parseInt(document.getElementById('m10').value);
                    const m11 = parseInt(document.getElementById('m11').value);
                    
                    if (isNaN(m00) || isNaN(m01) || isNaN(m10) || isNaN(m11)) {
                        showResult('Por favor, preencha todos os campos da matriz com números válidos', true);
                        return;
                    }
                    
                    const matriz = [[m00, m01], [m10, m11]];
                    const response = await fetch('/cifras/decifrarHill', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ texto, matriz })
                    });
                    resultado = await response.text();
                    if (!response.ok) erro = true;
                } else {
                    const chave = document.getElementById('chave').value.trim();
                    if (!chave) {
                        showResult('Por favor, digite uma chave válida', true);
                        return;
                    }
                    
                    const response = await fetch('/cifras/decifrar', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ texto, tipo: selectedCipher, chave })
                    });
                    resultado = await response.text();
                    if (!response.ok) erro = true;
                }
            } catch (e) {
                resultado = 'Erro de comunicação com o servidor. Por favor, tente novamente.';
                erro = true;
            } finally {
                showLoading(false);
            }

            showResult(resultado, erro);
        }

        function showResult(resultado, erro = false) {
            document.querySelectorAll('.step').forEach(div => div.classList.add('hidden'));
            const resultadoElem = document.getElementById('resultado');
            resultadoElem.innerText = resultado;
            resultadoElem.className = erro ? 'error' : '';
            document.getElementById('result').classList.remove('hidden');
        }

        function showLoading(show) {
            // Implemente um spinner ou mensagem de carregamento se desejar
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