export function addZeroIfBelowTen(value: number) {
    return value < 10 ? "0" + value : value;
}

export function dateParse(day: number, month: number, year: number) {
    return `${year}-${addZeroIfBelowTen(month)}-${addZeroIfBelowTen(day)}`;
}

export function getDateNow() {
    const date = new Date();
    const parsedDate = dateParse(date.getDate(), date.getMonth() + 1, date.getFullYear());
    return parsedDate;
}
