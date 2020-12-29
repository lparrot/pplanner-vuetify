export class ArrayUtils {
  static chunck(array: any[], nb: number) {
    return new Array(Math.ceil(array.length / nb))
      .fill(null)
      .map(_ => array.splice(0, nb))
  }
}
