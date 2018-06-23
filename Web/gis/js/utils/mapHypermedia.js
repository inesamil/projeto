export function mapJsonHomeToIndex (json) {
  const userResource = json.resources['/rel/user']
  const hrefTempl = userResource.hrefTemplate
  const hints = userResource.hints
  // const authSchema = hints.authSchemes.find(schema => schema.scheme === 'Basic')
  const index = {
    title: json.api.title,
    authorLink: json.api.links.author,
    hrefTempl
    // authSchema: authSchema.scheme
  }
  return index
}
