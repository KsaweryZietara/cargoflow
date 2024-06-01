export function isValidPesel(pesel: string) {
    const weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
    const controlNumber = parseInt(pesel.substring(10, 11));
    let sum = 0;

    for (let i = 0; i < weight.length; i++) {
        sum += (parseInt(pesel.substring(i, i + 1)) * weight[i]);
    }
    sum = sum % 10;
    return (10 - sum) % 10 === controlNumber;
}