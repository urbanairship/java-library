import re

from docutils import nodes
from docutils.parsers.rst.roles import set_classes
from docutils.parsers.rst import directives

from pygments.lexer import RegexLexer, bygroups
from pygments.lexers import get_lexer_by_name
from pygments.token import Literal, Text,  Operator, Keyword, Name, Number
from pygments.util import ClassNotFound

from sphinx import addnodes
from sphinx.roles import XRefRole
from sphinx.domains import Domain, ObjType, Index
from sphinx.directives import ObjectDescription
from sphinx.util.nodes import make_refnode
from sphinx.util.docfields import GroupedField, TypedField, Field
from sphinx.util.compat import Directive
from sphinx.util.compat import make_admonition

"""
class boundary_type(nodes.General, nodes.Element):
    pass


class BoundaryDirective(Directive):

    has_content = True
    required_arguments = 1
    optional_arguments = 0
    final_argument_whitespace = True
    option_spec = {
        'type': directives.unchanged,
    }

    def run(self):
        ret = []
        name = self.arguments[0]
        node = boundary_type()
        node.content = self.content

        node.type_ = self.options.get('type', 'boundary_type')
        return [node]


def html_visit_boundary(self, node):
    import pdb;pdb.set_trace()
    return '<p class="boundary">'

def html_depart_boundary(self, node):
    return '</p>'

def setup(app):
    app.add_directive('boundary', BoundaryDirective)
    app.add_node(boundary_type,
                 html=(html_visit_boundary, html_depart_boundary))
"""


class BoundaryResource(ObjectDescription):

    doc_field_types = [
        Field('type', label='Type', names=('type',)),
        GroupedField('alias', label='Aliases', names=('alias',)),
    ]

    option_spec = {
        'type': directives.unchanged,
    }


def setup(app):
    app.add_directive('boundary', BoundaryResource)
