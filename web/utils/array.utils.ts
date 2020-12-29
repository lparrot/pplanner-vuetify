export class ArrayUtils {
  static chunck(array: any[], nb: number) {
    const data = new Array(...array)
    return new Array(Math.ceil(data.length / nb))
      .fill(null)
      .map(_ => data.splice(0, nb))
  }
}
